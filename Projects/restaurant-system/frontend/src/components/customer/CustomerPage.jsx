import React, { useState, useEffect } from 'react';
import { menuAPI, tableAPI, orderAPI } from '../../services/api';

function CustomerPage() {
  const [categories, setCategories] = useState([]);
  const [menuItems, setMenuItems] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [tables, setTables] = useState([]);
  const [selectedTable, setSelectedTable] = useState(null);
  const [cart, setCart] = useState([]);
  const [notes, setNotes] = useState('');
  const [loading, setLoading] = useState(true);

  // Load dữ liệu khi component mount
  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const [categoriesRes, menuRes, tablesRes] = await Promise.all([
        menuAPI.getCategories(),
        menuAPI.getAvailable(),
        tableAPI.getAvailable()
      ]);
      
      setCategories(categoriesRes.data);
      setMenuItems(menuRes.data);
      setTables(tablesRes.data);
      
      if (categoriesRes.data.length > 0) {
        setSelectedCategory(categoriesRes.data[0].id);
      }
    } catch (error) {
      console.error('Lỗi load dữ liệu:', error);
      alert('Không thể tải dữ liệu. Vui lòng kiểm tra Backend!');
    } finally {
      setLoading(false);
    }
  };

  // Lọc món theo category
  const filteredItems = selectedCategory
    ? menuItems.filter(item => item.categoryId === selectedCategory)
    : menuItems;

  // Thêm món vào giỏ
  const addToCart = (item) => {
    const existingItem = cart.find(cartItem => cartItem.id === item.id);
    
    if (existingItem) {
      setCart(cart.map(cartItem =>
        cartItem.id === item.id
          ? { ...cartItem, quantity: cartItem.quantity + 1 }
          : cartItem
      ));
    } else {
      setCart([...cart, { ...item, quantity: 1, itemNotes: '' }]);
    }
  };

  // Giảm số lượng
  const decreaseQuantity = (itemId) => {
    setCart(cart.map(item =>
      item.id === itemId && item.quantity > 1
        ? { ...item, quantity: item.quantity - 1 }
        : item
    ).filter(item => item.quantity > 0));
  };

  // Xóa khỏi giỏ
  const removeFromCart = (itemId) => {
    setCart(cart.filter(item => item.id !== itemId));
  };

  // Tính tổng tiền
  const totalAmount = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);

  // Đặt món
  const handleOrder = async () => {
    if (!selectedTable) {
      alert('Vui lòng chọn bàn!');
      return;
    }

    if (cart.length === 0) {
      alert('Giỏ hàng trống!');
      return;
    }

    try {
      const orderData = {
        tableId: selectedTable,
        notes: notes,
        items: cart.map(item => ({
          menuItemId: item.id,
          quantity: item.quantity,
          notes: item.itemNotes || ''
        }))
      };

      await orderAPI.create(orderData);
      
      alert('Đặt món thành công! ✅');
      
      // Reset
      setCart([]);
      setNotes('');
      setSelectedTable(null);
      loadData(); // Reload để cập nhật bàn trống
    } catch (error) {
      console.error('Lỗi đặt món:', error);
      alert('Đặt món thất bại! ❌');
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-2xl">Đang tải...</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-100">
      {/* Header */}
      <div className="bg-blue-600 text-white p-6">
        <div className="container mx-auto">
          <h1 className="text-3xl font-bold">👥 Đặt Món</h1>
          <p className="mt-2">Chọn món ăn yêu thích của bạn</p>
        </div>
      </div>

      <div className="container mx-auto p-4">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Cột trái: Menu */}
          <div className="lg:col-span-2">
            {/* Categories */}
            <div className="bg-white rounded-lg shadow p-4 mb-4">
              <div className="flex gap-2 overflow-x-auto">
                {categories.map(cat => (
                  <button
                    key={cat.id}
                    onClick={() => setSelectedCategory(cat.id)}
                    className={`px-4 py-2 rounded-lg whitespace-nowrap transition ${
                      selectedCategory === cat.id
                        ? 'bg-blue-500 text-white'
                        : 'bg-gray-200 hover:bg-gray-300'
                    }`}
                  >
                    {cat.name}
                  </button>
                ))}
              </div>
            </div>

            {/* Menu Items */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {filteredItems.map(item => (
                <div key={item.id} className="bg-white rounded-lg shadow p-4 hover:shadow-lg transition">
                  <h3 className="text-lg font-bold text-gray-800">{item.name}</h3>
                  <p className="text-sm text-gray-600 mt-1">{item.description}</p>
                  <div className="flex justify-between items-center mt-4">
                    <span className="text-xl font-bold text-blue-600">
                      {item.price.toLocaleString('vi-VN')}đ
                    </span>
                    <button
                      onClick={() => addToCart(item)}
                      className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg transition"
                    >
                      + Thêm
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Cột phải: Giỏ hàng */}
          <div className="lg:col-span-1">
            <div className="bg-white rounded-lg shadow p-4 sticky top-4">
              <h2 className="text-xl font-bold mb-4">🛒 Giỏ hàng</h2>

              {/* Chọn bàn */}
              <div className="mb-4">
                <label className="block text-sm font-bold mb-2">Chọn bàn:</label>
                <select
                  value={selectedTable || ''}
                  onChange={(e) => setSelectedTable(parseInt(e.target.value))}
                  className="w-full border rounded-lg p-2"
                >
                  <option value="">-- Chọn bàn --</option>
                  {tables.map(table => (
                    <option key={table.id} value={table.id}>
                      Bàn {table.tableNumber} ({table.capacity} chỗ)
                    </option>
                  ))}
                </select>
              </div>

              {/* Items trong giỏ */}
              <div className="mb-4 max-h-64 overflow-y-auto">
                {cart.length === 0 ? (
                  <p className="text-gray-500 text-center py-4">Giỏ hàng trống</p>
                ) : (
                  cart.map(item => (
                    <div key={item.id} className="flex justify-between items-center mb-3 pb-3 border-b">
                      <div className="flex-1">
                        <p className="font-bold">{item.name}</p>
                        <p className="text-sm text-gray-600">
                          {item.price.toLocaleString('vi-VN')}đ x {item.quantity}
                        </p>
                      </div>
                      <div className="flex items-center gap-2">
                        <button
                          onClick={() => decreaseQuantity(item.id)}
                          className="bg-gray-300 hover:bg-gray-400 w-8 h-8 rounded"
                        >
                          -
                        </button>
                        <span className="w-8 text-center font-bold">{item.quantity}</span>
                        <button
                          onClick={() => addToCart(item)}
                          className="bg-blue-500 hover:bg-blue-600 text-white w-8 h-8 rounded"
                        >
                          +
                        </button>
                        <button
                          onClick={() => removeFromCart(item.id)}
                          className="bg-red-500 hover:bg-red-600 text-white w-8 h-8 rounded ml-2"
                        >
                          ×
                        </button>
                      </div>
                    </div>
                  ))
                )}
              </div>

              {/* Ghi chú */}
              <div className="mb-4">
                <label className="block text-sm font-bold mb-2">Ghi chú:</label>
                <textarea
                  value={notes}
                  onChange={(e) => setNotes(e.target.value)}
                  className="w-full border rounded-lg p-2"
                  rows="2"
                  placeholder="Ví dụ: Không hành, ít cay..."
                />
              </div>

              {/* Tổng tiền */}
              <div className="border-t pt-4 mb-4">
                <div className="flex justify-between text-xl font-bold">
                  <span>Tổng cộng:</span>
                  <span className="text-blue-600">
                    {totalAmount.toLocaleString('vi-VN')}đ
                  </span>
                </div>
              </div>

              {/* Button đặt món */}
              <button
                onClick={handleOrder}
                disabled={cart.length === 0 || !selectedTable}
                className={`w-full py-3 rounded-lg font-bold transition ${
                  cart.length === 0 || !selectedTable
                    ? 'bg-gray-300 cursor-not-allowed'
                    : 'bg-green-500 hover:bg-green-600 text-white'
                }`}
              >
                Đặt món
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CustomerPage;