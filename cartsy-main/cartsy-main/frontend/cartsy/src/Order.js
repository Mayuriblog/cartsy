import React, { useEffect, useState } from "react";

import $ from 'jquery';
import "../node_modules/jquery/dist/jquery.min.js";
import Carousel from 'react-bootstrap/Carousel';
import Modal from 'react-bootstrap/Modal';
import "../node_modules/bootstrap-table/dist/bootstrap-table.min.css";
import "../node_modules/bootstrap-table/dist/bootstrap-table.min.js";
import { useSearchParams } from "react-router-dom";




const Order = () => {


    var [order, setOrder] = useState({products:""});
    var [products, setProducts] = useState([]);
    
    const [searchParams, setSearchParams] = useSearchParams();

    const id = searchParams.get("id");
    
    function loadOrder() {
        const url = "http://localhost:8080/api/v1/private/orders/" + id;

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
                        setOrder(data);
                        loadProducts(data);
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });
    }

    function loadProducts(order) {

        if (order != undefined && order.productIds != undefined) {

            

            let orderProductsJSON = JSON.parse(order.productIds);

            let productList = [];

            Object.keys(orderProductsJSON).forEach((key) => {



                const url = "http://localhost:8080/api/v1/public/products/" + key;

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
                                data["qty"] = orderProductsJSON[key];
                                data["total"] = orderProductsJSON[key]*data["productSalePrice"];
                                productList.push(data);
                                setProducts(productList);

                                if(productList.length==Object.keys(orderProductsJSON).length){

                                    $('#table').bootstrapTable({
                                        pagination: true,
                                        search: true,
                                        sort: true,
                                        columns: [ {
                                          field: 'productName',
                                          title: 'Name',
                                          sortable: true,
                                        },
                                        {
                                            field: 'qty',
                                            title: 'Qty',
                                            sortable: true
                                          }, 
                                        {
                                            field: 'productSDesc',
                                            title: 'Description',
                                            sortable: true
                                          },{
                                          field: 'productActualPrice',
                                          title: 'Original Price',
                                          sortable: true
                                        },{
                                            field: 'productSalePrice',
                                            title: 'Price',
                                            sortable: true
                                          },
                                          {
                                            field: 'total',
                                            title: 'Total',
                                            sortable: true
                                          }

                                        ],
                                        data: productList
                                      });
                                
                                    }
                            });


                        } else {

                        }
                    })
                    .catch(error => { console.log('error', error) });

            });

        }

    }

    

    useEffect(() => {
        loadOrder();
        
    }, []);

    

    return (<div className="container">
        <br />
        <h3>{"Order #" + (order?order.id:"")}</h3>
        <div className="row">  
        <div className="float-end"> 
            <div className="btn-group">
                <a className="btn btn-warning" href="/return">Return</a>
                <a className="btn btn-success" href="/track">Track</a>

            </div>
        </div>
        </div>

        
        <div className="row">  

        <table id="table"></table>
        

     </div>

    </div>);
}

export default Order;