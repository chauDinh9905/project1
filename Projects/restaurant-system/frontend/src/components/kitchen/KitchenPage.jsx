import React, { useState, useEffect } from 'react';
import { orderAPI } from '../../services/api';

function KitchenPage() {
  const [allOrders, setAllOrders] = useState([]);
  const [filter, setFilter] = useState('active'); // 'active' | 'all'
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadOrders();
    const interval = setInterval(loadOrders, 30000);
    return () => clearInterval(interval);
  }, []);

  const loadOrders = async () => {
    try {
      setLoading(true);
      const response = await orderAPI.getAll();
      
      // Chỉ lấy orders của ngày hôm nay
      const today = new Date();
      today.setHours(0, 0, 0, 0);
      
      const todayOrders = response.data.filter(order => {
        const orderDate = new Date(order.createdAt);
        orderDate.setHours(0, 0, 0, 0);
        return orderDate.getTime() === today.getTime();
      });
      
      const sortedOrders = todayOrders.sort((a, b) => 
        new Date(b.createdAt) - new Date(a.createdAt)
      );
      
      setAllOrders(sortedOrders);
    } catch (error) {
      console.error('Lỗi load orders:', error);
      alert('Không thể tải orders!');
    } finally {
      setLoading(false);
    }
  };

  // Lọc orders theo filter
  const filteredOrders = filter === 'active'
    ? allOrders.filter(o => ['PENDING', 'PREPARING', 'READY'].includes(o.status))
    : allOrders;

  // Đếm số orders active
  const activeCount = allOrders.filter(o => 
    ['PENDING', 'PREPARING', 'READY'].includes(o.status)
  ).length;

  // Cập nhật trạng thái order
  const updateStatus = async (orderId, newStatus) => {
    try {
      await orderAPI.updateStatus(orderId, newStatus);
      loadOrders();
      alert(`Đã cập nhật trạng thái thành ${newStatus}! ✅`);
    } catch (error) {
      console.error('Lỗi cập nhật:', error);
      alert('Cập nhật thất bại! ❌');
    }
  };

  // Màu sắc theo status
  const getStatusColor = (status) => {
    switch (status) {
      case 'PENDING': return 'bg-yellow-100 text-yellow-800 border-yellow-300';
      case 'PREPARING': return 'bg-blue-100 text-blue-800 border-blue-300';
      case 'READY': return 'bg-green-100 text-green-800 border-green-300';
      case 'COMPLETED': return 'bg-gray-100 text-gray-800 border-gray-300';
      case 'CANCELLED': return 'bg-red-100 text-red-800 border-red-300';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  // Tên status tiếng Việt
  const getStatusText = (status) => {
    switch (status) {
      case 'PENDING': return '⏳ Chờ xử lý';
      case 'PREPARING': return '👨‍🍳 Đang nấu';
      case 'READY': return '✅ Đã xong';
      case 'COMPLETED': return '🎉 Hoàn thành';
      case 'CANCELLED': return '❌ Đã hủy';
      default: return status;
    }
  };

  // Buttons theo status
  const getStatusButtons = (order) => {
    switch (order.status) {
      case 'PENDING':
        return (
          <>
            <button
              onClick={() => updateStatus(order.id, 'PREPARING')}
              className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg transition"
            >
              Bắt đầu nấu
            </button>
            <button
              onClick={() => updateStatus(order.id, 'CANCELLED')}
              className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg transition"
            >
              Hủy
            </button>
          </>
        );
      case 'PREPARING':
        return (
          <button
            onClick={() => updateStatus(order.id, 'READY')}
            className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-lg transition"
          >
            Món đã xong
          </button>
        );
      case 'READY':
        return (
          <button
            onClick={() => updateStatus(order.id, 'COMPLETED')}
            className="bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded-lg transition"
          >
            Đã phục vụ
          </button>
        );
      default:
        return null;
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-2xl">Đang tải orders...</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-100">
      {/* Header */}
      <div className="bg-green-600 text-white p-6">
        <div className="container mx-auto">
          <div className="flex justify-between items-center">
            <div>
              <h1 className="text-3xl font-bold">👨‍🍳 Màn Hình Bếp</h1>
              <p className="mt-2">Quản lý và cập nhật đơn hàng - Hôm nay</p>
            </div>
            <button
              onClick={loadOrders}
              className="bg-white text-green-600 px-6 py-3 rounded-lg font-bold hover:bg-gray-100 transition"
            >
              🔄 Làm mới
            </button>
          </div>
        </div>
      </div>

      <div className="container mx-auto p-4">
        {/* Filter */}
        <div className="bg-white rounded-lg shadow p-4 mb-6">
          <div className="flex gap-4">
            <button
              onClick={() => setFilter('active')}
              className={`px-6 py-3 rounded-lg font-bold transition ${
                filter === 'active'
                  ? 'bg-green-500 text-white'
                  : 'bg-gray-200 hover:bg-gray-300'
              }`}
            >
              Orders đang xử lý ({activeCount})
            </button>
            <button
              onClick={() => setFilter('all')}
              className={`px-6 py-3 rounded-lg font-bold transition ${
                filter === 'all'
                  ? 'bg-green-500 text-white'
                  : 'bg-gray-200 hover:bg-gray-300'
              }`}
            >
              Tất cả orders hôm nay ({allOrders.length})
            </button>
          </div>
        </div>

        {/* Orders List */}
        {filteredOrders.length === 0 ? (
          <div className="bg-white rounded-lg shadow p-8 text-center">
            <p className="text-gray-500 text-xl">
              {filter === 'active' 
                ? 'Không có order đang xử lý!' 
                : 'Không có order nào hôm nay!'
              }
            </p>
          </div>
        ) : (
          <div className="grid grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 gap-6">
            {filteredOrders.map(order => (
              <div key={order.id} className="bg-white rounded-lg shadow-lg p-6 hover:shadow-xl transition">
                {/* Header */}
                <div className="flex justify-between items-start mb-4">
                  <div>
                    <h3 className="text-xl font-bold text-gray-800">
                      Bàn {order.tableNumber}
                    </h3>
                    <p className="text-sm text-gray-500">
                      {order.orderNumber}
                    </p>
                    <p className="text-xs text-gray-400 mt-1">
                      {new Date(order.createdAt).toLocaleString('vi-VN')}
                    </p>
                  </div>
                  <div className={`px-3 py-1 rounded-lg border-2 font-bold ${getStatusColor(order.status)}`}>
                    {getStatusText(order.status)}
                  </div>
                </div>

                {/* Items */}
                <div className="mb-4 border-t pt-4">
                  <h4 className="font-bold mb-2">Món đã đặt:</h4>
                  {order.items && order.items.map((item, index) => (
                    <div key={index} className="flex justify-between mb-2 text-sm">
                      <span>
                        <span className="font-bold">{item.quantity}x</span> {item.menuItemName}
                        {item.notes && (
                          <span className="text-gray-500 italic text-xs ml-2">
                            ({item.notes})
                          </span>
                        )}
                      </span>
                      <span className="text-gray-600">
                        {(item.price * item.quantity).toLocaleString('vi-VN')}đ
                      </span>
                    </div>
                  ))}
                </div>

                {/* Notes */}
                {order.notes && (
                  <div className="mb-4 bg-yellow-50 border border-yellow-200 rounded p-3">
                    <p className="text-sm">
                      <span className="font-bold">Ghi chú:</span> {order.notes}
                    </p>
                  </div>
                )}

                {/* Total */}
                <div className="border-t pt-4 mb-4">
                  <div className="flex justify-between text-lg font-bold">
                    <span>Tổng cộng:</span>
                    <span className="text-green-600">
                      {order.totalAmount.toLocaleString('vi-VN')}đ
                    </span>
                  </div>
                </div>

                {/* Action Buttons */}
                <div className="flex gap-2">
                  {getStatusButtons(order)}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default KitchenPage;