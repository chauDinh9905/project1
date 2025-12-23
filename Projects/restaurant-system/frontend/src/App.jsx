import React, { useState } from 'react';
import CustomerPage from './components/customer/CustomerPage';
import KitchenPage from './components/kitchen/KitchenPage';

function App() {
  const [currentPage, setCurrentPage] = useState('home');

  // Home page
  if (currentPage === 'home') {
    return (
      <div className="min-h-screen bg-gray-100">
        <div className="container mx-auto p-8">
          <h1 className="text-4xl font-bold text-center text-blue-600 mb-8">
            🍽️ Restaurant Order System
          </h1>
          
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {/* Card Khách hàng */}
            <div className="bg-white rounded-lg shadow-lg p-6 hover:shadow-xl transition">
              <h2 className="text-2xl font-bold text-gray-800 mb-4">
                👥 Màn hình Khách hàng
              </h2>
              <p className="text-gray-600 mb-4">
                Xem menu, chọn món và đặt hàng
              </p>
              <button
                onClick={() => setCurrentPage('customer')}
                className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-3 px-6 rounded-lg transition"
              >
                Vào đặt món →
              </button>
            </div>
            
            {/* Card Bếp */}
            <div className="bg-white rounded-lg shadow-lg p-6 hover:shadow-xl transition">
              <h2 className="text-2xl font-bold text-gray-800 mb-4">
                👨‍🍳 Màn hình Bếp
              </h2>
              <p className="text-gray-600 mb-4">
                Xem orders và cập nhật trạng thái
              </p>
              <button
                onClick={() => setCurrentPage('kitchen')}
                className="w-full bg-green-500 hover:bg-green-600 text-white font-bold py-3 px-6 rounded-lg transition"
              >
                Vào bếp →
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }

  // Customer page
  if (currentPage === 'customer') {
    return (
      <div>
        <button
          onClick={() => setCurrentPage('home')}
          className="fixed top-4 left-4 bg-gray-800 text-white px-4 py-2 rounded-lg z-50 hover:bg-gray-900"
        >
          ← Về trang chủ
        </button>
        <CustomerPage />
      </div>
    );
  }

  // Kitchen page
  if (currentPage === 'kitchen') {
    return (
      <div>
        <button
          onClick={() => setCurrentPage('home')}
          className="fixed top-4 left-4 bg-gray-800 text-white px-4 py-2 rounded-lg z-50 hover:bg-gray-900"
        >
          ← Về trang chủ
        </button>
        <KitchenPage />
      </div>
    );
  }
}

export default App;