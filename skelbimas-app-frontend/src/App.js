import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import UserService from "./service/UserService/UserService";
import LoginPage from './pages/auth/LoginPage';
import Dashboard from './pages/Dashboard';
import Navbar from './components/Navbar/Navbar';
import RegistrationPage from './pages/auth/RegistrationPage';
import AllPosts from './pages/AllPosts';
import AdminDashboard from './pages/AdminDashboard';
import AddCategory from './pages/categories/AddCategory';
import EditCategory from './pages/categories/EditCategory';
import AddPost from './pages/posts/AddPost';
import EditPost from './pages/posts/EditPost';
import ViewPost from './pages/posts/ViewPost';

function App() {
  const isAuthenticated = UserService.isAuthenticated();
  const isAdmin = UserService.isAdmin();

  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={isAuthenticated ? <Navigate to="/dashboard" /> : <LoginPage />} />
        <Route path="/register" element={isAuthenticated ? <Navigate to="/dashboard" /> : <RegistrationPage />} />

        {/* Authenticated User Routes */}
        <Route path="/dashboard" element={isAuthenticated ? <Dashboard /> : <Navigate to="/" />} />
        <Route path="/all-posts" element={isAuthenticated ? <AllPosts /> : <Navigate to="/" />} />

        <Route path="/view-post/:id" element={<ViewPost />} />


        {/* Admin Routes */}
        {isAdmin && isAuthenticated && (
          <>
            {/* Add your admin routes here */}
            <Route path="/admin-dashboard" element={<AdminDashboard />} />

            <Route path="/add-category" element={<AddCategory />} />
            <Route path="/edit-category/:id" element={<EditCategory />} />

            <Route path="/add-post" element={<AddPost />} />
            <Route path="/edit-post/:id" element={<EditPost />} />
          </>
        )}

        {/* Redirect to login if not authenticated and trying to access a non-public route */}
        <Route path="*" element={<Navigate to={isAuthenticated ? "/dashboard" : "/"} />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
