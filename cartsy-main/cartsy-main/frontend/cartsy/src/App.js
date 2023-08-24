import React, {Component} from 'react';
import './App.css';
import "../node_modules/jquery/dist/jquery.min.js";
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import Login from './Login'
import Register from './Register'
import Profile from './Profile';
import Navbar from './Navbar';
import Home from './Home'
import MyShop from './MyShop';
import Deal from './Deal';
import Deals from './Deals';
import Search from './Search';
import Latest from './Latest';
import BestSeller from './BestSeller';
import Cart from './Cart';
import Checkout from './Checkout';
import Orders from './Orders';
import Order from './Order';
import Product from './Product';
import SellerProduct from './SellerProduct';
import AdminSettings from "./AdminSettings";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import "../node_modules/bootstrap/dist/js/bootstrap.min.js"





function App() {
  
  return (
    <div>
      <React.StrictMode>
    
    <BrowserRouter>
      <Navbar/>
      <Routes>
        <Route path="/admin" element={<AdminSettings/>}/>
        <Route path="/login" element={<Login/>}/>
        <Route path="/register" element={<Register/>}/>
        <Route path="/profile" element={<Profile/>}/>
        <Route path="/home" element={<Home/>}/>
        <Route path="/deals" element={<Deals/>}/>
        <Route path="/deal" element={<Deal/>}/>
        <Route path="/search" element={<Search/>}/>
        <Route path="/bestseller" element={<BestSeller/>}/>
        <Route path="/latest" element={<Latest/>}/>
        <Route path="/product" element={<Product/>}/>
        <Route path="/cart" element={<Cart/>}/>
        <Route path="/checkout" element={<Checkout/>}/>
        <Route path="/orders" element={<Orders/>}/>
        <Route path="/order" element={<Order/>}/>
        <Route path="/myshop" element={<MyShop/>}/>
        <Route path="/sproduct" element={<SellerProduct/>}/>
      </Routes>
    </BrowserRouter>
    </React.StrictMode>
    </div>
  );
  
}

export default App;
