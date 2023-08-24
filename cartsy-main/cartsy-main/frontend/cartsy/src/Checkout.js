import React, { useEffect, useState } from "react";
import Carousel from 'react-bootstrap/Carousel';
import Modal from 'react-bootstrap/Modal';
import $ from 'jquery';
import jQuery from 'jquery';
import "bootstrap-table/dist/bootstrap-table.min.js";
import "bootstrap-table/dist/bootstrap-table.min.css";
import { Alert, Button } from "react-bootstrap";





const Checkout = () => {

    const [showAlert, setShowAlert] = useState(false);

    var [cart, setCart] = useState({ products: "" });
    var [products, setProducts] = useState([]);
    var [addresses, setAddresses] = useState([]);
    var [paymentinfos, setPaymentInfos] = useState([]);


    function loadCart() {
        const url = "http://localhost:8080/api/v1/private/buyers/cart/" + localStorage.getItem("id");

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
                        setCart(data);
                        loadProducts(data);
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });
    }


    function loadAddresses() {
        const url = "http://localhost:8080/api/v1/private/address/" + localStorage.getItem("id");

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
                        setAddresses(data);
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });




    }

    function loadPaymentinfos() {
        const url = "http://localhost:8080/api/v1/private/paymentinfo/" + localStorage.getItem("id");

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
                        setPaymentInfos(data);
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });




    }

    function loadProducts(cart) {

        if (cart != undefined && cart.products != undefined && cart.products != '') {

            

            let cartProductsJSON = JSON.parse(cart.products);

            let productList = [];

            Object.keys(cartProductsJSON).forEach((key) => {



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
                                data["qty"] = cartProductsJSON[key];
                                data["total"] = cartProductsJSON[key]*data["productSalePrice"];
                                productList.push(data);
                                setProducts(productList);

                                if(productList.length==Object.keys(cartProductsJSON).length){

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


    function placeOrder(e){
        e.preventDefault();
        const newOrder = {};


        newOrder['ecomUserId'] = localStorage.getItem("id");
        newOrder['shippingAddress'] = e.target.elements.shippingAddress.value;
        newOrder['modeOfPayment'] = e.target.elements.paymentMethod.value;
        newOrder['productIds'] = cart.products;
        


        const url = "http://localhost:8080/api/v1/private/orders";

        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Accept", "application/json");
        myHeaders.append("Authorization", "Bearer " + localStorage.getItem("jwt"));


        var raw = JSON.stringify(
            newOrder
        );

        console.log(raw);

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw
        };

        fetch(url, requestOptions)
            .then(response => {

                if (response.status === 200) {
                    setShowAlert(true);
                    response.json().then(data => {
                        console.log(data);

                       
                    })

                } else {

                }

            })
            .catch(error => { console.log('error', error) });

    }

    useEffect(() => {
        loadCart();
        loadAddresses();
        loadPaymentinfos();

    }, []);



    return (<div className="container">
        <br />
        {
        showAlert ? <Alert variant="success" onClose={() => setShowAlert(false)} dismissible>
            <Alert.Heading>
                <p>Order placed successfully, <a href="/home">continue shopping...</a>!</p>

            </Alert.Heading>

            
        </Alert> : ""
        }
        <form onSubmit={placeOrder}>
        <div className="row">
            <div className="col-md">
                <h3>Shipping</h3>

                <div className="row">
                    {

                        addresses !== undefined ? addresses.map(item => {
                            return (


                                <div className="row">
                                    <div className="col-md-1">
                                        <input type="radio" name="shippingAddress" value={item.hNo+", "+item.line1+ ", " + item.line2 + ", " + item.city + ", " + item.state +", " + item.country +", " + item.pincode + ". Phone " + item.phone}></input>
                                    </div><div className="col-md-11">
                                        <div key={item.id} className="card" >

                                            <div className="card-body">

                                                <h5 className="card-title">{item.country}</h5>
                                                <p className="card-text">{item.hNo}</p>
                                                <p className="card-text">{item.line1}</p>
                                                <p className="card-text">{item.line2}</p>
                                                <p className="card-text">{item.city}</p>
                                                <p className="card-text">{item.state}</p>
                                                <p className="card-text">{item.pincode}</p>
                                                <p className="card-text">{item.phone}</p>



                                            </div>
                                        </div>
                                    </div>
                                </div>



                            );
                        }) : ""
                    }
                </div>
            </div>
            <div className="col-md">
                <h3>Payment</h3>
                <div className="row">
                    {

                        paymentinfos !== undefined ? paymentinfos.map(item => {
                            return (


                                <div className="row">
                                    <div className="col-md-1">
                                        <input type="radio" name="paymentMethod" value={item.cardNo}></input>
                                    </div><div className="col-md-11">
                                        <div key={item.id} className="card" >

                                            <div className="card-body">
                                                <h2 className="card-text">{item.cardName}</h2>
                                                <p className="card-title">{item.cardNo}</p>
                                                <p className="card-text">{item.cardDoe}</p>

                                            </div>
                                        </div>
                                    </div>
                                </div>



                            );
                        }) : ""
                    }
                </div>
            </div>
        </div>
        <hr/>
        <div className="row">
            <h3>Products</h3>   
            <table id="table"></table>


        </div>
        <div className="row">
         
            {
                showAlert?
            <button className="btn btn-success disabled" disabled>Place Order</button>
            :            <button className="btn btn-success">Place Order</button>}

        </div>
        <hr/>
                    </form>
    </div>);
}

export default Checkout;