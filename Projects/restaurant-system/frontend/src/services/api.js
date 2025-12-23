import axios from 'axios';

// Base URL của Backend
const API_BASE_URL = 'http://localhost:8080/api';

// Tạo axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// MENU APIs
export const menuAPI = {
  // Lấy tất cả món
  getAll: () => api.get('/menu'),
  
  // Lấy món theo ID
  getById: (id) => api.get(`/menu/${id}`),
  
  // Lấy món available
  getAvailable: () => api.get('/menu/available'),
  
  // Lấy món theo category
  getByCategory: (categoryId) => api.get(`/menu/category/${categoryId}`),
  
  // Lấy tất cả categories
  getCategories: () => api.get('/menu/categories'),
};

//TABLE APIs
export const tableAPI = {
  // Lấy tất cả bàn
  getAll: () => api.get('/tables'),
  
  // Lấy bàn trống
  getAvailable: () => api.get('/tables/available'),
  
  // Cập nhật status bàn
  updateStatus: (id, status) => api.patch(`/tables/${id}/status?status=${status}`),
};

// ORDER APIs 
export const orderAPI = {
  // Tạo order mới
  create: (orderData) => api.post('/orders', orderData),
  
  // Lấy tất cả orders
  getAll: () => api.get('/orders'),
  
  // Lấy order theo ID
  getById: (id) => api.get(`/orders/${id}`),
  
  // Lấy orders đang active
  getActive: () => api.get('/orders/active'),
  
  // Lấy orders theo bàn
  getByTable: (tableId) => api.get(`/orders/table/${tableId}`),
  
  // Cập nhật status order
  updateStatus: (id, status) => api.patch(`/orders/${id}/status?status=${status}`),
};

export default api;