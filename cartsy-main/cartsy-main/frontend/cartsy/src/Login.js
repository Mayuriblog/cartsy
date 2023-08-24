import React, { Component } from 'react';
import { useNavigate } from 'react-router';
import jwt_decode from "jwt-decode";



const Login = () => {

    

    localStorage.clear();
    const navigate = useNavigate();

    const login = (e) => {
        e.preventDefault();
        
        const userCredentials = {};
    
        userCredentials['username'] = e.target.elements.username.value;
        userCredentials['password'] = e.target.elements.password.value;
    
        const url = "http://localhost:8080/api/v1/public/login";
    
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Accept", "application/json");
    
        var raw = JSON.stringify(
            userCredentials
        );
    
        console.log(raw);
    
         var requestOptions = {
             method: 'POST',
             headers: myHeaders,
             body: raw
         };
    
         fetch(url, requestOptions)
             .then(response => {
                if(response.status===200){
                    
                    response.json().then((data) => {
                        localStorage.setItem("jwt", data.message);
                        localStorage.setItem("subject", jwt_decode(data.message).sub);
                        localStorage.setItem("role", jwt_decode(data.message).ROLE);
                        localStorage.setItem("id", jwt_decode(data.message).ID);


                        navigate("/home");
                    });
                    
                    
                }else{
                   
                }
            })
             .catch(error => {console.log('error', error)});
    }



   
        return (
            <div className='Custom-center-form-container'>
                <div className='Custom-center-form'>
                    <div className='h2'><p>Welcome. Please sign-in...</p></div>
                    <form onSubmit={login}>
                    <div className='form-group'>
                        <input name="username" type="text" className='form-control mt-1' placeholder="username"></input>
                        <input name="password" type="password" className='form-control mt-1' placeholder="password"></input>
                    </div>
                    <br />
                    <button className='btn btn-success form-control'>Signin</button>
                    </form>
                </div>
            </div>

        );
}

export default Login;