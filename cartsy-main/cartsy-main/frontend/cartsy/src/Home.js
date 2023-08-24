import React from 'react';
import { useNavigate } from "react-router";
import { useState, useEffect } from 'react';
import Carousel from 'react-bootstrap/Carousel';



const Home = () => {

    var [deals, setDeals] = useState([]);


    function loadDeals() {
        const url = "http://localhost:8080/api/v1/public/deals";

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
                        setDeals(data);
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });
    }

    useEffect(() => {
       

        loadDeals();
    }, []);

    
    return (<div>
         <br />
                <div className="container">
                    <br />
                    <div className="row">
                        
                    <div className="col">
                    <Carousel >
                        {
                        deals !== undefined ? deals.map(item => {
                                return (
                                    <Carousel.Item>
                                        <a href={'/deal?q='+item.id} >
                                        <img
                                            
                                            src={"http://localhost:8080/api/v1/public/deals/" + item.id + "/images/1"}
                                            alt="Front"
                                        />
                                        <Carousel.Caption>
                                            <h3>{item.dealName}</h3>
                                        </Carousel.Caption>
                                        </a>
                                    </Carousel.Item>

                                    



                                );
                            }) : ""
                        }
                    </Carousel>
                    </div>
                    </div>


                </div>
    </div>);
}

export default Home;