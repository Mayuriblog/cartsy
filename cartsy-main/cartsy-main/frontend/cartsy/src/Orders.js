import React, { useEffect, useState } from "react";

import $ from 'jquery';
import jQuery from 'jquery';
import Carousel from 'react-bootstrap/Carousel';
import Modal from 'react-bootstrap/Modal';
import "bootstrap-table/dist/bootstrap-table.min.js";
import "bootstrap-table/dist/bootstrap-table.min.css";




const Orders = () => {


    var [orders, setOrders] = useState([]);
    
    
    function loadOrders() {
        const url = "http://localhost:8080/api/v1/private/orders/buyer/?buyerId=" + localStorage.getItem("id");

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

                    response.json().then((ordersData) => {
                        setOrders(ordersData);
                        $('#table').bootstrapTable({
                            pagination: true,
                            search: true,
                            sort: true,
                            columns: [ {
                                field: 'id',
                                title: 'Order ID',
                                sortable: true,
                            }, 
                            {
                                field: 'dateOfOrder',
                                title: 'Placed On',
                                sortable: true
                            },{
                                field: 'price',
                                title: 'Price',
                                sortable: true
                                },{
                                    field: 'status',
                                    title: 'Status',
                                    sortable: true
                                    },{
                                        field: 'id',
                                        title: '',
                                        formatter:(value, row)=> {
                                            return '<a href="/order?id='+value+'">Details</a>'; 
                                        }
                                    }

                            ],
                            data: ordersData
                            });
                                
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });
    }

    

    useEffect(() => {
        loadOrders();
        
    }, []);

    

    return (<div className="container">
        <br />
        <h3>My Orders</h3>

        <table id="table"></table>
        

     

    </div>);
}

export default Orders;