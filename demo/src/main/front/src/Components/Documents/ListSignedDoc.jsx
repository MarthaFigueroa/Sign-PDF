import React, { useEffect, useState } from 'react';
import { firestore, storage } from '../../firebaseConfig'
// import { useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify';
// import { Link } from 'react-router-dom';

const ListDoc = () => {

    const [docs, setDocs] = useState([]);

    // const navigate = useNavigate();

    // const goTo = (route) =>{
    //     console.log("kkk");
    //     navigate(route);
    // }

    const onDeleteDoc = async (id, filename) =>{
        console.log(id);
        const confirmation = window.confirm("Are you sure you want to delete this signed document?");
        if(confirmation===true){
            await firestore.collection('documents').doc(id).delete();
            let imageRef = storage.refFromURL(`gs://validacion-de-documentos.appspot.com/originalDocuments/${filename}`);
            await imageRef.delete();
            toast('Signed Document Removed Successfully', {
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
    
    useEffect(() => {
        const getDocs = () =>{
            firestore.collection('documents').onSnapshot(snapshot => {
                const docs = [];
                snapshot.forEach((doc) => {
                    docs.push({...doc.data().SignedFile, id: doc.id});
                });
                setDocs(docs);
            });
        }
        getDocs();
    }, []);
    

    return (
        <>
            <div className="col-md-6 col-sm-12 offset-md-3">
                <div className="form-group input-group formField">
                    <div className="input-group-text icon searchIconDiv">
                        <i className="material-icons searchIcon">search</i>
                    </div>
                    <input type="text" placeholder="Buscar usuario" id="doc_search" onKeyUp={searchUsr}/>
                </div>
            </div>
            <ul id="usersList" className="row">
                {docs.map(doc =>{
                    return (
                            <div key={doc.id} className="col-md-4 docs-div">
                                <li key={doc.id}>
                                <div className="card mb-3 text-center">
                                    <div className="card-header text-white contacts-header  justify-content-between">
                                        <h2>{doc.Signed_filename}</h2>
                                        {/* <div className='text-dark close-card'>
                                            <Link to="/signedDocuments">
                                                <i className="material-icons" onClick={() => onDeleteDoc(doc.id)}>close</i>
                                            </Link>
                                            <a href={doc.Signed_file} download={`Signed_${doc.Filename}`} target="_blank" rel="noreferrer">                                                 
                                                <i className="material-icons">download</i>
                                            </a>
                                        </div> */}
                                    </div>
                                    <div className="card-body">
                                        <div><b>Creator User: </b>{doc.Signers}</div>
                                        <div><b>CSV: </b>{doc.CSV}</div>
                                        {/* <div><b>Signed: </b> <a id="downloadLink" href={doc.Signed_file} download={`Signed_${doc.Filename}`} target="_blank" rel="noreferrer">{doc.Filename}</a></div> */}
                                        {/* <iframe src="https://giphy.com/embed/oZ7zyrQwFaRyM" frameBorder="0" className="giphy-embed" allowFullScreen></iframe> */}
                                    </div>
                                    <div className="card-footer">
                                        <a href="/documents" className="btn btn-delete" onClick={() => onDeleteDoc(doc.id, doc.Filename)}>Delete</a>
                                        <a href={doc.Signed_file} className="btn btn-download" download={`Signed_${doc.Filename}`} target="_blank" rel="noreferrer">                                                 
                                                {/* <i className="material-icons">download</i> */}
                                                {/* {doc.Filename} */}
                                                Descargar
                                            </a>
                                    </div>
                                </div>
                            </li>   
                        </div>
                    )
                })}
            </ul>
        </>
    );
}

export default ListDoc;

