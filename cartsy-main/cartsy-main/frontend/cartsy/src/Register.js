import React from 'react';
import { useNavigate } from "react-router-dom";

const Register = () => {
    const navigate = useNavigate();

    const register = (e) => {
        e.preventDefault();
        
        const newUser = {};
    
        newUser['username'] = e.target.elements.username.value;
        newUser['email'] = e.target.elements.email.value;
        newUser['type'] = e.target.elements.role.value;
        newUser['password'] = e.target.elements.password.value;
    
        const url = "http://localhost:8080/api/v1/public/register";
    
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Accept", "application/json");
    
        var raw = JSON.stringify(
            newUser
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
                    navigate("/login");
                    
                }else{
                    
                }
                
            })
             .catch(error => {console.log('error', error)});
    }

    
    return (
        
        <div className='Custom-center-form-container'>
            <div className='Custom-center-form'>
                <div className='h2'><p>Welcome, please register...</p></div>
                <form onSubmit={register}>
                    <div className='form-group'>
                        <select name="role" className='form-control' id="role">
                            <option value="ROLE_CARTSY_SELLER">Seller</option>
                            <option value="ROLE_CARTSY_BUYER">Buyer</option>
                        </select>
                        <input type="text" name="username" className='form-control mt-1' placeholder="username"></input>
                        <input type="email" name="email" className='form-control mt-1' placeholder="email"></input>
                        <input type="password" name="password" className='form-control mt-1' placeholder="password"></input>
                        <input type="password" name="confirm_password" className='form-control mt-1' placeholder="confirm password"></input>
                    </div>
                    <br />
                    <button className='btn btn-primary form-control'>Register</button>
                </form>
            </div>
        </div>

    );
    
}

export default Register;



