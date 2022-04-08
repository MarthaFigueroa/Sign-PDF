import React, { useEffect, useState } from 'react';
import { firestore } from '../../firebaseConfig'
import { toast } from 'react-toastify';
import { Link } from 'react-router-dom';

const ListLink = () => {

    const [links, setLinks] = useState([]);

    const onDeleteLink = async id =>{
        console.log(id);
        if(window.confirm("Are you sure you want to delete this link?")){
            await firestore.collection('links').doc(id).delete();
            toast('Link Removed Successfully', {
                type: 'error',
                autoClose: 2000
            });
        }
    }

    // const onCreatedLink = () =>{
    //     toast('Link Removed Successfully', {
    //         type: 'error',
    //         autoClose: 2000,
    //         icon: ({theme, type}) =>  <img src="../logo.svg"/>
    //     });
    // }

    const searchUsr = () => {
        var input, filter, ul, li, h2, i, txtValue,div;
        input = document.getElementById("doc_search"); 
        filter = input.value.toUpperCase(); 
        ul = document.getElementById("usersList"); 
        li = ul.getElementsByTagName("li");        console.log(li.length);
        div = document.getElementsByClassName(" docs-div");
        for (i = 0; i < li.length; i++) {  
        h2 = li[i].getElementsByTagName("h2")[0]; 
        txtValue = h2.textContent || h2.innerText; //a.textContent || a.innerText; 
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
            div[i].style.visibility="visible";
            div[i].style.position="relative";
        } else {
            li[i].style.display = "none";
            div[i].style.visibility="hidden";
            div[i].style.position="absolute";
        }
        }
    }
    
    useEffect(() => {
        const getLinks = () =>{
            firestore.collection('links').onSnapshot(snapshot => {
                const docs = [];
                snapshot.forEach((doc) => {
                    docs.push({...doc.data(), id: doc.id});
                });
                setLinks(docs);
            });
        }
        getLinks();
    }, []);
    

    return (
        <>
            <div className="col-md-6 col-xs-2 offset-md-3">
                <div className="form-group input-group formField">
                    <div className="input-group-text icon searchIconDiv">
                        <i className="material-icons searchIcon">search</i>
                    </div>
                    <input type="text" placeholder="Buscar usuario" id="doc_search" onKeyUp={searchUsr}/>
                </div>
            </div>
            {/* <ToastContainer /> */}
            <ul id="usersList" className="row">
                {links.map(link =>{
                    return (
                        <div key={link.id} className="col-md-4 docs-div">
                            <li key={link.id}>
                                <div className="card mb-3 text-center">
                                    <div className="card-header text-white contacts-header  justify-content-between">
                                        <h2>{link.firstname}  {link.lastname}</h2>
                                        <div className='text-dark close-card'>
                                            <Link to="/links">
                                                <i className="material-icons" onClick={() => onDeleteLink(link.id)}>close</i>
                                            </Link>
                                            <Link to={`/editLink/${link.id}`}>
                                                <i className="material-icons" >create</i>
                                                {/* onClick={() => onEditLink(link.id)} */}
                                            </Link>

                                        </div>
                                    </div>
                                    <div className="card-body">
                                        <div><b>Email: </b>{link.email}</div>
                                        <div><b>Phone: </b>{link.phone}</div>
                                    </div>
                                    {/* <div className="card-footer">
                                        <a href="/" className="btn btn-danger" onClick={() => onDeleteLink(link.id)}>Delete</a>
                                        <a href="/" className="btn btn-info">Update</a>
                                    </div> */}
                                </div>
                            </li>
                        </div>
                    )
                })}
            </ul>
        </>
    );
}

export default ListLink;

