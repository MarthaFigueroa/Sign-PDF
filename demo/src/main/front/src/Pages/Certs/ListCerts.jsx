import React, { useEffect, useState, useContext } from 'react';
import { ToastContainer } from 'react-toastify';
import ListCert from '../../Components/Certificates/ListCert';
import Navbar from '../../Components/Partials/Navbar';
import { useNavigate } from "react-router-dom";
import { storage } from '../../Config/config'
import { toast } from 'react-toastify';
import axios from '../../axios.js';
import { UserContext } from '../../Providers/UserProvider';

const ListDocs = () => {
    const user = useContext(UserContext);
    const [certs, setCerts] = useState([]);
    const navigate = useNavigate();

    const onDeleteCert = async (id, filename) =>{
        console.log(id);
        const confirmation = window.confirm("Are you sure you want to delete this certificate?");
        if(confirmation===true){
  
            await axios.post(`/deleteCertificate/${id}`, {
              headers: {
                Accept: "application/json ,text/plain, */*"
              }
            })
            .then(async res => {
              console.log(res.data);
              let imageRef = storage.refFromURL(`gs://validacion-de-documentos.appspot.com/certificates/${filename}`);
              await imageRef.delete();
              toast('¡El certificado ha sido eliminado!', {
                  type: 'error',
                  autoClose: 2000
              });
              setCerts(res.data);
            })
        }
    }

    useEffect(() => {
        function validateUser(user){
            if(user !== null){
                if (user.role === "admin") {
                    const getCerts = async() =>{
                        await axios.get(`/certificates`, {
                            headers: {
                                Accept: "application/json ,text/plain, */*"
                            }
                        })
                        .then(async res => {
                        console.log(res.data);
                        setCerts(res.data);
                        })
                    }
                    getCerts();
                }
                else{
                    navigate("/documents");
                }
            }
        }
        console.log("User", user);
        validateUser(user);
          
    }, [user, navigate]);

    return(
        <>
        {user === null ? null: user.role === "admin" ? 
            <div>
                <Navbar />
                <div>
                    <ToastContainer />
                    <ListCert certs={certs} onDeleteCert={onDeleteCert}/>    
                </div>
            </div>
            : null
        }
        </>
    )
}

export default ListDocs;