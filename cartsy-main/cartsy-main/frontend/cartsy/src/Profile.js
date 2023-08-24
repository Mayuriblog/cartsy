import React, { useEffect, useState } from "react";
import Carousel from 'react-bootstrap/Carousel';
import Modal from 'react-bootstrap/Modal';
import $ from 'jquery';
import jQuery from 'jquery';
import "bootstrap-table/dist/bootstrap-table.min.js";
import "bootstrap-table/dist/bootstrap-table.min.css";
import { Button } from "react-bootstrap";
import "../node_modules/@fortawesome/fontawesome-free/css/all.min.css"





const Profile = () => {


    var [buyer, setBuyer] = useState({});
    var [addresses, setAddresses] = useState([]);
    var [paymentinfos, setPaymentInfos] = useState([]);


    function loadBuyer() {
        const url = "http://localhost:8080/api/v1/private/buyers/" + localStorage.getItem("id");

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
                        setBuyer(data);
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });
    }

    const saveAddress = (e) => {
        e.preventDefault();

        const newAddress = {};

        newAddress['ecomUserId'] = localStorage.getItem("id");
        newAddress['hNo'] = e.target.elements.hNo.value;
        newAddress['line1'] = e.target.elements.line1.value;
        newAddress['line2'] = e.target.elements.line2.value;
        newAddress['city'] = e.target.elements.city.value;
        newAddress['state'] = e.target.elements.state.value;
        newAddress['country'] = e.target.elements.country.value;
        newAddress['pincode'] = e.target.elements.pincode.value;
        newAddress['phone'] = e.target.elements.phone.value;



        const url = "http://localhost:8080/api/v1/private/address";

        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Accept", "application/json");
        myHeaders.append("Authorization", "Bearer " + localStorage.getItem("jwt"));


        var raw = JSON.stringify(
            newAddress
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
                    loadAddresses();
                    response.json().then(data => {
                        console.log(data);

                    })

                } else {

                }

            })
            .catch(error => { console.log('error', error) });

    }

    const savePaymentInfo = (e) => {
        e.preventDefault();

        const newPaymentMethod = {};

        newPaymentMethod['ecomUserId'] = localStorage.getItem("id");
        newPaymentMethod['cardName'] = e.target.elements.cardName.value;
        newPaymentMethod['cardNo'] = e.target.elements.cardNo.value;
        newPaymentMethod['cardCvv'] = e.target.elements.cardCvv.value;
        newPaymentMethod['cardDoe'] = e.target.elements.cardDoe.value;



        const url = "http://localhost:8080/api/v1/private/paymentinfo";

        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Accept", "application/json");
        myHeaders.append("Authorization", "Bearer " + localStorage.getItem("jwt"));


        var raw = JSON.stringify(
            newPaymentMethod
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
                    loadPaymentinfos();
                    response.json().then(data => {
                        console.log(data);

                    })

                } else {

                }

            })
            .catch(error => { console.log('error', error) });

    }


    function saveBuyer() {
        const url = "http://localhost:8080/api/v1/private/buyers/" + buyer.id;

        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Accept", "application/json");

        myHeaders.append("Authorization", "Bearer " + localStorage.getItem("jwt"));

        var requestOptions = {
            method: 'PUT',
            headers: myHeaders,
            body: JSON.stringify(buyer)

        };

        fetch(url, requestOptions)
            .then(response => {
                if (response.status === 200) {

                    response.json().then((data) => {
                        // setBuyer(data);
                    });


                } else {

                }
            })
            .catch(error => { console.log('error', error) });
    }

    function deleteAddress(addressId) {
        alert("cal")
        const url = "http://localhost:8080/api/v1/private/address/" + addressId;

        var myHeaders = new Headers();
        myHeaders.append("Accept", "application/json");

        myHeaders.append("Authorization", "Bearer " + localStorage.getItem("jwt"));

        var requestOptions = {
            method: 'DELETE',
            headers: myHeaders

        };

        fetch(url, requestOptions)
            .then(response => {
                if (response.status === 200) {

                    loadAddresses();


                } else {

                }
            })
            .catch(error => { console.log('error', error) });
    }

    function deletePaymentInfo(paymentInfoId) {
        alert("cal2")

        const url = "http://localhost:8080/api/v1/private/paymentinfo/" + paymentInfoId;

        var myHeaders = new Headers();
        myHeaders.append("Accept", "application/json");

        myHeaders.append("Authorization", "Bearer " + localStorage.getItem("jwt"));

        var requestOptions = {
            method: 'DELETE',
            headers: myHeaders

        };

        fetch(url, requestOptions)
            .then(response => {
                if (response.status === 200) {

                    loadPaymentinfos();


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

    function handleChange(e) {
        let profileUpdate = buyer;
        profileUpdate[e.target.name] = e.target.value;
        setBuyer(profileUpdate);
    }

    useEffect(() => {
        loadBuyer();
        loadAddresses();
        loadPaymentinfos();

    }, []);


    const [showAddress, setShowAddress] = useState(false);


    const handleAddressClose = () => setShowAddress(false);
    const handleAddressShow = () => setShowAddress(true);

    const [showPaymentInfo, setShowPaymentInfo] = useState(false);


    const handlePaymentInfoClose = () => setShowPaymentInfo(false);
    const handlePaymentInfoShow = () => setShowPaymentInfo(true);



    return (<div className="container">
        <br />



        <div className="row">
            <div className="col">
                <h3>General</h3>
            </div>
            <div className="col">
                <div className="float-end">
                    <div className="btn-group">
                        <button className="btn btn-success" onClick={saveBuyer}>Save</button>
                        <button className="btn btn-warning">Reset</button>
                    </div>
                </div>
            </div>
            <div className="form-group row">
                <label className="col-sm-2 col-form-label">Name</label>
                <div className="col-sm-10">
                    <input className='form-control mt-1' type="text" name="buyerName" defaultValue={buyer.buyerName} onChange={handleChange}></input>
                </div>
            </div>
            <div className="form-group row">
                <label className="col-sm-2 col-form-label">Phone</label>
                <div className="col-sm-10">
                    <input className='form-control mt-1' type="text" name="buyerPhone" defaultValue={buyer.buyerPhone} onChange={handleChange}></input>
                </div>
            </div>
            <div className="form-group row">
                <label className="col-sm-2 col-form-label">Email</label>
                <div className="col-sm-10">
                    <input className='form-control mt-1' type="text" name="buyerEmail" defaultValue={buyer.buyerEmail} onChange={handleChange}></input>
                </div>
            </div>


        </div>
        <hr />
        <div className="row">
            <div className="col">
                <h3>Shipping</h3>
            </div>
            <div className="col">
                <div className="float-end">
                    <div className="btn-group">
                        <button className="btn btn-success" onClick={handleAddressShow}>Add</button>
                    </div>
                </div>
            </div>

            <div className="row">
                {

                    addresses !== undefined ? addresses.map(item => {
                        return (


                            <div className="col-md-4 mt-2">
                                <div key={item.id} className="card" >
                                    <div class="card-header">
                                    <h5 className="card-title">{item.country}</h5>

                                        <div className="float-end">
                                            <div className="btn-group">
                                                <button className="fa-solid fa-trash" onClick={()=>deleteAddress(item.id)}></button>
                                                <button className="fa-solid fa-pen"></button>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="card-body">

                                        <p className="card-text">{item.hNo}</p>
                                        <p className="card-text">{item.line1}</p>
                                        <p className="card-text">{item.line2}</p>
                                        <p className="card-text">{item.city}</p>
                                        <p className="card-text">{item.state}</p>
                                        <p className="card-text">{item.phone}</p>



                                    </div>
                                </div>
                            </div>



                        );
                    }) : ""
                }
            </div>
        </div>


        <hr />
        <div className="row">
            <div className="col">
                <h3>Payment</h3>
            </div>
            <div className="col">
                <div className="float-end">
                    <div className="btn-group">
                        <button className="btn btn-success" onClick={handlePaymentInfoShow}>Add</button>
                    </div>
                </div>
            </div>
            <div className="row">
                {

                    paymentinfos !== undefined ? paymentinfos.map(item => {
                        return (


                            <div className="col-md-4 mt-2">
                                <div key={item.id} className="card" >
                                <div class="card-header">
                                    <h5 className="card-title">{item.cardName}</h5>

                                        <div className="float-end">
                                            <div className="btn-group">
                                                <button className="fa-solid fa-trash" onClick={()=>deletePaymentInfo(item.id)}></button>
                                                <button className="fa-solid fa-pen"></button>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="card-body">
                                        <p className="card-title">{item.cardNo}</p>
                                        <p className="card-text">{item.cardDoe}</p>

                                    </div>
                                </div>
                            </div>



                        );
                    }) : ""
                }
            </div>

            <hr />

        </div>


        <Modal show={showAddress} onHide={handleAddressClose}>
            <Modal.Header closeButton>
                <Modal.Title>New Address</Modal.Title>
            </Modal.Header>
            <form onSubmit={saveAddress}>

                <Modal.Body>
                    <div className='form-group'>

                        <input name="hNo" type="text" className='form-control mt-1' placeholder="House No."></input>
                        <input name="line1" type="text" className='form-control mt-1' placeholder="Line 1"></input>
                        <input name="line2" type="text" className='form-control mt-1' placeholder="Line 2"></input>
                        <input name="city" type="text" className='form-control mt-1' placeholder="City"></input>
                        <input name="state" type="text" className='form-control mt-1' placeholder="State"></input>
                        <input name="country" type="text" className='form-control mt-1' placeholder="Country"></input>
                        <input name="pincode" type="text" className='form-control mt-1' placeholder="Pincode"></input>
                        <input name="phone" type="text" className='form-control mt-1' placeholder="Phone"></input>



                    </div>
                </Modal.Body>
                <Modal.Footer>

                    <button className="btn btn-primary" onClick={handleAddressClose}>
                        Save Changes
                    </button>
                </Modal.Footer>
            </form>
        </Modal>

        <Modal show={showPaymentInfo} onHide={handlePaymentInfoClose}>
            <Modal.Header closeButton>
                <Modal.Title>New Payment Method</Modal.Title>
            </Modal.Header>
            <form onSubmit={savePaymentInfo}>

                <Modal.Body>
                    <div className='form-group'>
                        <input name="cardName" type="text" className='form-control mt-1' placeholder="Card Holder Name"></input>
                        <input name="cardNo" type="text" className='form-control mt-1' placeholder="Card No."></input>
                        <input name="cardCvv" type="text" className='form-control mt-1' placeholder="CVV"></input>
                        <input name="cardDoe" type="text" className='form-control mt-1' placeholder="Expiry"></input>


                    </div>
                </Modal.Body>
                <Modal.Footer>

                    <button className="btn btn-primary" onClick={handlePaymentInfoClose}>
                        Save Changes
                    </button>
                </Modal.Footer>
            </form>
        </Modal>

    </div>);
}

export default Profile;