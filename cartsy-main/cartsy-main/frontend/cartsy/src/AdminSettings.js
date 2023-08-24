import React from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { useState, useEffect } from 'react';
import Select from 'react-select'



const AdminSettings = () => {

    const [deals, setDeals] = useState([]);
    const [products, setProducts] = useState([]);

    const [productList, setProductList] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);

    const [selectedProducts, setSelectedProducts] = useState([]);

    const handleChange = (options) => {
        var selectedIds = '';
        options.forEach(element => {
            selectedIds= selectedIds!==''?selectedIds+','+element.value:element.value;
        })
        setSelectedProducts(selectedIds);

    };


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

                        var productOptions = [];
                        data.forEach(element => {
                            productOptions.push({ value: element.id, label: element.productName });
                        });
                        setProductList(productOptions);
                        
                        setFilteredProducts(data);
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });
    }

    const saveDeal = (e) => {
        e.preventDefault();

        const newDeal = {};

        newDeal['dealName'] = e.target.elements.dealName.value;
        newDeal['dealDesc'] = e.target.elements.dealDesc.value;
        newDeal['dealUrl'] = e.target.elements.dealUrl.value;
        newDeal['dealCategory'] = e.target.elements.dealCategory.value;
        newDeal['dealStartDate'] = e.target.elements.dealStartDate.value;
        newDeal['dealEndDate'] = e.target.elements.dealEndDate.value;

        newDeal['dealProducts'] = selectedProducts;


        const url = "http://localhost:8080/api/v1/private/admin/deals";

        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Accept", "application/json");
        myHeaders.append("Authorization", "Bearer " + localStorage.getItem("jwt"));


        var raw = JSON.stringify(
            newDeal
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
                    loadDeals();
                    response.json().then(data => {
                        console.log(data);
                        saveDealImages(e, data.message);
                    })

                } else {

                }

            })
            .catch(error => { console.log('error', error) });

    }

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

    function saveDealImages(e, id) {
        const baseurl = "http://localhost:8080/api/v1/private/admin/deals/" + id + "/images/";

        for (let i = 1; i <= 1; i++) {

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





    useEffect(() => {
        loadDeals();
        loadProducts();
       
    }, []);




    const [show, setShow] = useState(false);


    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <div>
            <div className="container">
                <br />
                <h2 className="muted">All Deals</h2>
                <br />
                <div className='row'>
                    <div className='col-md-2'>
                        <Button variant="primary" onClick={handleShow}>
                            Add
                        </Button>
                    </div>
                </div>

                <br />
                <div className="container">
                    <br />
                    <div className="row">
                        {

                            deals !== undefined ? deals.map(item => {
                                return (


                                    <div className="col-md-4 mt-2">
                                        <div key={item.id} className="card" >
                                            <img className="card-img-top" src={"http://localhost:8080/api/v1/public/deals/" + item.id + "/images/1"}></img>

                                            <div className="card-body">

                                                <h5 className="card-title">{item.productName}</h5>
                                                <p className="card-text">{item.productSDesc}</p>
                                                <a href={"/sproduct?id=" + item.id} className="btn btn-primary">Details</a>
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
                    <Modal.Title>New Deal</Modal.Title>
                </Modal.Header>
                <form onSubmit={saveDeal}>

                    <Modal.Body>
                        <div className='form-group'>

                            <select name="dealCategory" type="text" className='form-control mt-1' >

                                <option value="HomePage720">Home Page 720px </option>;
                                <option value="HomePage400">Home Page 400px </option>;


                            </select>
                            <input name="dealName" type="text" className='form-control mt-1' placeholder="Name"></input>
                            <textarea name="dealDesc" type="text" className='form-control mt-1' placeholder="Description"></textarea>
                            <input name="dealUrl" type="text" className='form-control mt-1' placeholder="URL for the products."></input>
                            <input name="dealStartDate" type="date" className='form-control mt-1' placeholder="Start Date"></input>
                            <input name="dealEndDate" type="date" className='form-control mt-1' placeholder="End Date"></input>
                            <Select onChange={handleChange} id="dealProducts" name="dealProducts" options={productList} isMulti/>
                                
                            
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label col-form-label-sm">Banner</label>
                                <div className="col-sm-10">
                                    <input name="image_1" type="file" accept="image/png, image/jpg, image/jpeg" className='form-control mt-1'></input>
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

export default AdminSettings;