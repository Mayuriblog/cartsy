import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import Carousel from 'react-bootstrap/Carousel';
import Modal from 'react-bootstrap/Modal';

import { Alert } from "react-bootstrap";
import "../node_modules/@fortawesome/fontawesome-free/css/all.min.css"






const Product = () => {

    const [showAlert, setShowAlert] = useState(false);

    var [categories, setCategories] = useState([]);

    var [reviews, setReviews] = useState([]);

    var [product, setProduct] = useState({});
    const [searchParams, setSearchParams] = useSearchParams();

    const id = searchParams.get("id");

    const [show, setShow] = useState(false);


    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const addToCart = (e) => {
        e.preventDefault();




        const url = "http://localhost:8080/api/v1/private/buyers/cart/" + localStorage.getItem("id") + "?productId=" + product.id;

        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Accept", "application/json");
        myHeaders.append("Authorization", "Bearer " + localStorage.getItem("jwt"));


        var raw = JSON.stringify(
            product
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
                    loadProduct();
                    response.json().then(data => {
                        console.log(data);

                    })

                } else {

                }

            })
            .catch(error => { console.log('error', error) });

    }

    const saveReview = (e) => {
        e.preventDefault();

        const newReview = {};

        newReview['ecomUser'] = localStorage.getItem("id");
        newReview['ecomUsername'] = localStorage.getItem("subject");

        newReview['productId'] = id;
        newReview['reviewDetails'] = e.target.elements.reviewDetails.value;
        newReview['reviewRating'] = e.target.elements.reviewRating.value;



        const url = "http://localhost:8080/api/v1/private/reviews";

        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Accept", "application/json");
        myHeaders.append("Authorization", "Bearer " + localStorage.getItem("jwt"));


        var raw = JSON.stringify(
            newReview
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
                    loadReviews();
                    response.json().then(data => {
                        console.log(data);
                    })

                } else {

                }

            })
            .catch(error => { console.log('error', error) });

    }



    function loadCategories() {
        const url = "http://localhost:8080/api/v1/private/categories";

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
                        setCategories(data);
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });
    }

    function loadReviews() {
        const url = "http://localhost:8080/api/v1/private/reviews/" + id;

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
                        setReviews(data);
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });
    }


    function handleChange(e) {
        let productUpdate = product;
        console.log(e.target.name + ":" + e.target.value);
        productUpdate[e.target.name] = e.target.value;
        console.log(JSON.stringify(productUpdate))
        setProduct(productUpdate);
    }
    function loadProduct() {
        const url = "http://localhost:8080/api/v1/public/products/" + id;

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
                        setProduct(data);
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });
    }

    useEffect(() => {
        loadProduct();
        loadReviews();
        loadCategories();
    }, []);

    function resolveCategory(categoryId) {
        if (categories != undefined && categoryId != undefined && categoryId !== "") {

            categories.forEach(element => {
                if (element.id == categoryId) {

                    return (element.levels);
                }
            });
        }
    }
    return (<div className="container">

        {
            showAlert ? <Alert variant="success" onClose={() => setShowAlert(false)} dismissible>
                <Alert.Heading>
                    <p>Product added successfully to the <a href="/cart">cart</a>!</p>

                </Alert.Heading>


            </Alert> : ""
        }

        <nav aria-label="breadcrumb">
            <ol className="breadcrumb">
                <li className="breadcrumb-item"><a href="/">Home</a></li>
                <li className="breadcrumb-item"><a href="/search">Search</a></li>
                <li className="breadcrumb-item active" aria-current="page">{product != undefined ? resolveCategory(product.categoryId) : ""}</li>
            </ol>
        </nav>

        <div className="float-end">
            <div className="btn-group">
                <button className="btn btn-success">Buy</button>
                <button className="btn btn-warning" onClick={addToCart}>Add To Cart</button>
            </div>
        </div>



        <div className="row">
            <div className="col-md-4">
                <Carousel className="Custom-carousel">
                    <Carousel.Item>
                        <img
                            className="Custom-carousel"
                            src={"http://localhost:8080/api/v1/public/products/" + id + "/images/1"}
                            alt="Front"
                        />
                        <Carousel.Caption>
                            <h3>Front</h3>
                        </Carousel.Caption>
                    </Carousel.Item>
                    <Carousel.Item>
                        <img
                            className="Custom-carousel"
                            src={"http://localhost:8080/api/v1/public/products/" + id + "/images/2"}
                            alt="Left"
                        />
                        <Carousel.Caption>
                            <h3>Left</h3>
                        </Carousel.Caption>
                    </Carousel.Item>
                    <Carousel.Item>
                        <img
                            className="Custom-carousel"
                            src={"http://localhost:8080/api/v1/public/products/" + id + "/images/3"}
                            alt="Right"
                        />
                        <Carousel.Caption>
                            <h3>Right</h3>
                        </Carousel.Caption>
                    </Carousel.Item>
                    <Carousel.Item>
                        <img
                            className="Custom-carousel"
                            src={"http://localhost:8080/api/v1/public/products/" + id + "/images/4"}
                            alt="Top"
                        />
                        <Carousel.Caption>
                            <h3>Top</h3>
                        </Carousel.Caption>
                    </Carousel.Item></Carousel>
            </div>
            <div className="col-md-8">
                <h3>{product != undefined ? product.productName : ""}</h3>

                <p>{product.productSDesc}</p>
                <p>{product.productLDesc}</p>
                <p>Actual Price: {product.productActualPrice}</p>
                <p>Sale Price: {product.productSalePrice}</p>
                <p>Brand: {product.brand}</p>
            </div>
        </div>
        <br />
        <div className="row">
            <div className="container">
                <h3>Reviews</h3>
                <div className="float-end">
                    <div className="btn-group">
                        <button className="btn btn-success" onClick={handleShow}>Add Review</button>
                    </div>
                </div>

            </div>


        </div>
        <hr/>
        <div className="row">
            {

                reviews.map(item => {
                    return (


                        <div className="row">
                            <div key={item.id} className="card" >

                                <div className="card-body">
                                    <p className="card-text">
                                        {
                                            Array.from(Array(item.reviewRating), (e,i) => {
                                                return  <i class="fa-solid fa-star"></i>
                                            })        
                                            
                                        }

                                        {
                                            Array.from(Array(5 - item.reviewRating), (e,i) => {
                                                return  <i class="fa-regular fa-star"></i>
                                            })        
                                            
                                        }

                                        
                                    </p>

                                    <b className="card-text">{item.ecomUsername}</b>


                                    <p className="card-text">{item.reviewDetails}</p>
                                </div>
                            </div>
                        </div>



                    );
                })
            }
        </div>
            <hr/>
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>New Review</Modal.Title>
            </Modal.Header>
            <form onSubmit={saveReview}>

                <Modal.Body>
                    <div className='form-group'>
                        <div className="form-group row">
                            <label className="col-sm-2 col-form-label col-form-label-sm">Rating</label>
                            <div className="col-sm-10">
                                <input name="reviewRating" type="range" min="1" max="5" className='form-control mt-1' ></input></div>
                        </div>

                        <textarea name="reviewDetails" type="text" className='form-control mt-1' placeholder="Details"></textarea>


                    </div>
                </Modal.Body>
                <Modal.Footer>

                    <button className="btn btn-success" onClick={handleClose}>
                        Save Changes
                    </button>
                </Modal.Footer>
            </form>
        </Modal>

    </div>);
}

export default Product;