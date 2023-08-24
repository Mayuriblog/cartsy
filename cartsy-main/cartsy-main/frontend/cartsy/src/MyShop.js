import React from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { useState, useEffect } from 'react';

const MyShop = () => {

    var [categories, setCategories] = useState(categories);
    var [products, setProducts] = useState(products);
    var [filteredProducts, setFilteredProducts] = useState(filteredProducts);

    const saveProduct = (e) => {
        e.preventDefault();

        const newProduct = {};

        newProduct['sellerId'] = localStorage.getItem("id");
        newProduct['categoryId'] = e.target.elements.categoryId.value;
        newProduct['productName'] = e.target.elements.productName.value;
        newProduct['productSDesc'] = e.target.elements.productSDesc.value;
        newProduct['productLDesc'] = e.target.elements.productLDesc.value;
        newProduct['price'] = e.target.elements.price.value;
        newProduct['quantity'] = e.target.elements.quantity.value;
        newProduct['brand'] = e.target.elements.brand.value;


        const url = "http://localhost:8080/api/v1/private/products";

        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Accept", "application/json");
        myHeaders.append("Authorization", "Bearer " + localStorage.getItem("jwt"));


        var raw = JSON.stringify(
            newProduct
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
                    loadProducts();
                    response.json().then(data => {
                        console.log(data);
                        saveProductImages(e, data.message);
                    })

                } else {

                }

            })
            .catch(error => { console.log('error', error) });

    }

    function saveProductImages(e, id) {
        const baseurl = "http://localhost:8080/api/v1/private/products/" + id + "/images/";

        for (let i = 1; i <= 4; i++) {

            if (e.target.elements["image_" + i].files[0] != undefined) {
                console.log("saving file..." + i);
                let url = baseurl + i;
                const formData = new FormData();
                formData.append("file", e.target.elements["image_" + i].files[0]);

                var myHeaders = new Headers();
                myHeaders.append("Authorization", "Bearer " + localStorage.getItem("jwt"));

                var requestOptions = {
                    method: 'POST',
                    headers: myHeaders,
                    body: formData
                };

                fetch(url, requestOptions)
                    .then(response => {

                        if (response.status === 200) {
                            console.log("saved image... " + i);

                        } else {
                            console.log("failed to save image... " + i);
                        }

                    })
                    .catch(error => { console.log('error', error) });
            }

        }

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


    function loadProducts() {
        const url = "http://localhost:8080/api/v1/public/products/seller/" + localStorage.getItem("id");

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
        loadCategories();
        loadProducts();
    }, []);

    function search(e) {
        var searchText = e.target.value;
        if (searchText == undefined || searchText == '') {
            setFilteredProducts(products);
        }
        var filteredSet = [];
        products.forEach(element => {
            if (element.productName.includes(searchText)) {
                filteredSet.push(element);
            }
        });
        setFilteredProducts(filteredSet);

    }


    const [show, setShow] = useState(false);


    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <div>
            <div className="container">
                <br />
                <h2 className="muted">My Products</h2>
                <br />
                <div className='row'>
                    <div className='col-md-2'>
                        <Button variant="primary" onClick={handleShow}>
                            Add Product
                        </Button>
                    </div>
                    <div className='col-md-2'> <input placeholder='search...' onChange={search}></input></div>
                </div>

                <br />
                <div className="container">
                    <br />
                    <div className="row">
                        {

                            filteredProducts !== undefined ? filteredProducts.map(item => {
                                return (


                                    <div className="col-md-4 mt-2">
                                        <div key={item.id} className="card" >
                                            <img className="card-img-top" src={"http://localhost:8080/api/v1/public/products/" + item.id + "/images/1"}></img>

                                            <div className="card-body">

                                                <h5 className="card-title">{item.productName}</h5>
                                                <p className="card-text">{item.productSDesc}</p>
                                                <a href={"/sproduct?id="+item.id} className="btn btn-primary">Details</a>
                                            </div>
                                        </div>
                                    </div>



                                );
                            }) : ""
                        }
                    </div>


                </div>

            </div>


            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>New Product</Modal.Title>
                </Modal.Header>
                <form onSubmit={saveProduct}>

                    <Modal.Body>
                        <div className='form-group'>

                            <select name="categoryId" type="text" className='form-control mt-1' >
                                {
                                    categories != undefined ? categories.map(item => {
                                        return (<option value={item.id}>{item.levels}</option>);
                                    }) : ""
                                }
                            </select>
                            <input name="productName" type="text" className='form-control mt-1' placeholder="Name"></input>
                            <input name="productSDesc" type="text" className='form-control mt-1' placeholder="Short Description"></input>
                            <textarea name="productLDesc" type="text" className='form-control mt-1' placeholder="Long Description"></textarea>
                            <input name="price" type="text" className='form-control mt-1' placeholder="Price"></input>
                            <input name="quantity" type="text" className='form-control mt-1' placeholder="Quantity"></input>
                            <input name="brand" type="text" className='form-control mt-1' placeholder="Brand"></input>
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label col-form-label-sm">Front View</label>
                                <div className="col-sm-10">
                                    <input name="image_1" type="file" accept="image/png, image/jpg, image/jpeg" className='form-control mt-1'></input>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label col-form-label-sm">Left View</label>
                                <div className="col-sm-10">
                                    <input name="image_2" type="file" accept="image/png, image/jpg, image/jpeg" className='form-control mt-1' ></input>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label col-form-label-sm">Right View</label>
                                <div className="col-sm-10">
                                    <input name="image_3" type="file" accept="image/png, image/jpg, image/jpeg" className='form-control mt-1' ></input>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label col-form-label-sm">Top View</label>
                                <div className="col-sm-10">
                                    <input name="image_4" type="file" accept="image/png, image/jpg, image/jpeg" className='form-control mt-1' ></input>
                                </div>
                            </div>


                        </div>
                    </Modal.Body>
                    <Modal.Footer>

                        <button className="btn btn-primary" onClick={handleClose}>
                            Save Changes
                        </button>
                    </Modal.Footer>
                </form>
            </Modal>
        </div>
    );

}

export default MyShop;