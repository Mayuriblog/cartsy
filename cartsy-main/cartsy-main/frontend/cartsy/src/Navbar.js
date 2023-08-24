import React from 'react';
import { useNavigate } from "react-router";


const Navbar = () => {
    const username = localStorage.getItem("subject");
    const role = localStorage.getItem("role");
    const navigate = useNavigate();

    const handleSearch = (e) =>{
        e.preventDefault();
        localStorage.setItem("q","");
        if(e.target.elements.search.value!=undefined){
            navigate("/search"+"?q="+e.target.elements.search.value);
        }

    }

    

    return (<div>
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container-fluid">
                <a className="navbar-brand" href="/home"><b><label className='text-warning'>Cart</label><label className='text-success'>sy</label></b></a>

                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarSupportedContent">

                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <a className="nav-link" href="/deals">Deals</a>
                        </li>

                        <li className="nav-item">
                            <a className="nav-link" href="/bestseller" tabindex="-1">Bestsellers</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/latest" tabindex="-1">Latest</a>
                        </li>
                    </ul>
                    < div className="container">
                        <form onSubmit={handleSearch} className="d-flex justify-content-center">
                            <input name="search" id="search" className="form-control me-2" type="search" placeholder="Search" aria-label="Search" />
                            <button className="btn btn-outline-success"  >Search</button>

                        </form>
                    </div>
                    <div className="collapse navbar-collapse" id="navbarNavDarkDropdown">
                        <ul className="navbar-nav">
                            <li className="nav-item dropdown">
                                <a className="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    {username!=undefined?username:'Sign-in'}
                                </a>
                                <ul className="dropdown-menu dropdown-menu-dark dropdown-menu-end" aria-labelledby="navbarDarkDropdownMenuLink">
                                    {role==="ROLE_CARTSY_SELLER"?<li><a className="dropdown-item" href="/myshop">My Shop</a></li>:""}
                                    {role==="ROLE_CARTSY_BUYER"?<li><a className="dropdown-item" href="/cart">My Cart</a></li>:""}
                                    {role==="ROLE_CARTSY_BUYER"?<li><a className="dropdown-item" href="/orders">My Orders</a></li>:""}
                                    
                                    <li><a className="dropdown-item" href="/profile">Profile</a></li>

                                    <li><a className="dropdown-item" href="/login">{username!=undefined?'Sign-out':'Sign-in'}</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>





                </div>
            </div>
        </nav>
    </div>);
}

export default Navbar;