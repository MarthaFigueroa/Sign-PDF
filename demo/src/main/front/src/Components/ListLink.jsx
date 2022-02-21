import React, { useEffect, useState } from 'react';
import { db } from '../firebaseConfig'
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom'

const ListLink = () => {

    const [links, setLinks] = useState([]);
    const navigate = useNavigate();

    const onDeleteLink = async id =>{
        console.log(id);
        if(window.confirm("Are you sure you want to delete this link?")){
            await db.collection('links').doc(id).delete();
            toast('Link Removed Successfully', {
                type: 'error',
                autoClose: 2000
            });
        }
    }

    const onEditLink = async id =>{
        console.log(id);
        navigate(`/editLink/${id}`);
    }


    const getLinks = async () =>{
        db.collection('links').onSnapshot(snapshot => {
            const docs = [];
            snapshot.forEach((doc) => {
                docs.push({...doc.data(), id: doc.id});
            });
            console.log(docs);
            setLinks(docs);
        });
    }

    useEffect(() => {
        getLinks();
    }, []);
    

    return (
        <>
            {links.map(link =>{
                return (
                    <div key={link.id} className="col-md-4">
                        <div className="card mb-3">
                            <div className="card-header text-white contacts-header d-flex justify-content-between">
                                <h2>{link.firstname}  {link.lastname}</h2>
                                <div className='text-dark close-card'>
                                    <i className="material-icons" onClick={() => onDeleteLink(link.id)}>close</i>
                                    <i className="material-icons" onClick={() => onEditLink(link.id)}>create</i>

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
                    </div>
                )
            })}
        </>
    );
}

export default ListLink;

