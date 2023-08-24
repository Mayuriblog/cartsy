import React, { useEffect } from "react";
import { useState } from "react";
import { useSearchParams } from "react-router-dom";


const Latest = () => {

    const [products, setProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);


    function loadProducts() {
        const url = "http://localhost:8080/api/v1/public/products/latest";
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Accept", "application/json");

        myHeaders.append("Authorization", "Bearer " + localStorage.getItem("jwt"));

        var requestOptions = {
            method: 'GET',
            headers: myHeaders,

        };
        fetch(url, requestOptions)
            .then(response => {
                if (response.status === 200) {

                    response.json().then((data) => {
                        setProducts(data);
                        setFilteredProducts(data);
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });
    }

    useEffect(() => {
            loadProducts();
    }, []);
    

   

        
    
  


    return (
        <div>
            <div className="container">
                <br />
                <h2 className="muted">Latest</h2>
                <br />
                    <div className="row">
                        {

                            filteredProducts.map(item => {
                                return (


                                    <div className="col-md-4 mt-2">
                                        <div key={item.id} className="card" >
                                            <img className="card-img-top" src={"http://localhost:8080/api/v1/public/products/" + item.id + "/images/1"}></img>

                                            <div className="card-body">

                                                <h5 className="card-title">{item.productName}</h5>
                                                <p className="card-text">{item.productSDesc}</p>
                                                <a href={"/product?id="+item.id} className="btn btn-warning">Details</a>
                                            </div>
                                        </div>
                                    </div>



                                );
                            }) 
                        }
                    </div>


                </div>
        </div>
    );
}

export default Latest;