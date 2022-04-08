import React, { useEffect, useState } from 'react';
import { firestore, storage } from '../../firebaseConfig'
import { useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify';
import { Link } from 'react-router-dom';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
// import {library} from '@fortawesome/fontawesome-svg-core';
// import * as Icons from '@fortawesome/free-solid-svg-icons';

const ListDoc = () => {

    const [docs, setDocs] = useState([]);

    // const navigate = useNavigate();

    // const goTo = (route) =>{
    //     console.log("kkk");
    //     navigate(route);
    // }

    const onDeleteDoc = async (id, filename) =>{
        console.log(id);
        const confirmation = window.confirm("Are you sure you want to delete this document?");
        if(confirmation===true){
            await firestore.collection('documents').doc(id).delete();
            let imageRef = storage.refFromURL(`gs://validacion-de-documentos.appspot.com/originalDocuments/${filename}`);
            await imageRef.delete();
            toast('Document Removed Successfully', {
                type: 'error',
                autoClose: 2000
            });
        }
            
    }

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

    // const onCreatedDoc = () =>{
    //     toast('Doc Removed Successfully', {
    //         type: 'error',
    //         autoClose: 2000,
    //         icon: ({theme, type}) =>  <img src="../logo.svg"/>
    //     });
    // }
    
    useEffect(() => {
        const getDocs = () =>{
            firestore.collection('documents').onSnapshot(snapshot => {
                const docs = [];
                snapshot.forEach((doc) => {
                    docs.push({...doc.data(), id: doc.id});
                });
                console.log(docs);
                setDocs(docs);
            });
        }
        getDocs();
    }, []);
    

    return (
        <div key="1">
            <div key="searchBar" className="col-md-6 col-xs-2 offset-md-3">
                <div className="form-group input-group formField">
                    <div className="input-group-text icon searchIconDiv">
                        <i className="material-icons searchIcon">search</i>
                    </div>
                    <input className="form-control" type="text" placeholder="Buscar usuario" id="doc_search" onKeyUp={searchUsr} aria-label="Search"/>
                </div>
            </div>
            {/* <ToastContainer /> */}
            <ul id="usersList" className="row">
                {docs.map((doc) =>{
                    return (
                        <div key={doc.id} className="col-md-4 docs-div">
                            <li key={doc.id}>
                                <div className="card mb-3 text-center">
                                    <div className="card-header text-white contacts-header  justify-content-between">
                                        <div className="">
                                            <h2>{doc.OriginalFile.Filename}</h2>
                                        </div>
                                        {/* <div className='text-dark close-card'>
                                            <Link to="/documents">
                                                <i className="material-icons" onClick={() => onDeleteDoc(doc.OriginalFile.id, doc.OriginalFile.Filename)}>close</i>
                                            </Link>
                                            <a href={doc.OriginalFile.File} download={doc.OriginalFile.Filename} target="_blank" rel="noreferrer">                                                 
                                                <i className="material-icons">download</i>
                                            </a>
                                        </div> */}
                                    </div>
                                    <div className="card-body">
                                        <div><b>Creator User: </b>{doc.OriginalFile.Signers}</div>
                                        {/* <div><b>Email: </b>{doc.OriginalFile.email}</div>
                                        <div><b>Hash: </b>{doc.OriginalFile.File_hash}</div>                                 */}
                                        {/* <div><b>Signed: </b> <button className='btn btn-light' id="downloadLink" onClick={() => downloadFile(doc.OriginalFile.File, doc.OriginalFile.filename)}>Chale</button></div> */}
                                        <div><b>Original: </b><a href={doc.OriginalFile.File} download={doc.OriginalFile.Filename} target="_blank" rel="noreferrer"> {doc.OriginalFile.Filename} </a></div>
                                        {/* <iframe src="https://giphy.com/embed/xDQ3Oql1BN54c" frameBorder="0" className="giphy-embed" allowFullScreen></iframe> */}
                                    </div>
                                    <div className="card-footer">
                                        <Link to="/documents" className="btn btn-delete" onClick={() => onDeleteDoc(doc.id, doc.OriginalFile.Filename)}>Delete</Link>
                                        <a href={doc.OriginalFile.File} className="btn btn-download" download={doc.OriginalFile.Filename} target="_blank" rel="noreferrer">                                                 
                                                {/* <i className="material-icons">download</i> */}
                                                {/* {doc.Filename} */}
                                                Descargar
                                        </a>
                                        <a href={doc.SignedFile.Signed_file} className="btn btn-download" download={doc.SignedFile.Signed_file} target="_blank" rel="noreferrer">                                                 
                                                {/* <i className="material-icons">download</i> */}
                                                {/* {doc.Filename} */}
                                                Descargar Firmado
                                        </a>
                                    </div>
                                </div>
                            </li>   
                        </div>
                    )
                })}
            </ul>
        </div>
    )
}

export default ListDoc;

