import {React, useContext, useEffect } from 'react';
import Searchbar from '../Partials/Searchbar';
import Card from '../Partials/Card';
import { useState } from 'react';
import { storage } from '../../Config/config'
import { toast } from 'react-toastify';
import axios from '../../axios.js';
import { useNavigate } from "react-router-dom";
import { UserContext } from '../../Providers/UserProvider';

const ListCert = () => {
  const user = useContext(UserContext);
  const [certs, setCerts] = useState([]);
  const navigate = useNavigate();    

  const onDeleteCert = async (id, filename) =>{
    const confirmation = window.confirm("Are you sure you want to delete this certificate?");
    if(confirmation===true){

        await axios.post(`/deleteCertificate/${id}`, {
          headers: {
            Accept: "application/json ,text/plain, */*"
          }
        })
        .then(async res => {
          let imageRef = storage.refFromURL(`gs://validacion-de-documentos.appspot.com/certificates/${filename}`);
          await imageRef.delete();
          await toast('¡El certificado ha sido eliminado!', {
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
      validateUser(user);
          
    }, [user, navigate]);

  return (
    <>
      {user === null ? null: user.role === "admin" ? 
        <div className='content'>
          <Searchbar type='cert'/>
          <main className="h-full md:h-screen w-full">
            <section className="container mx-auto px-0 md:px-4 py-4">
              <ul id="List">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4 justify-items-center gap-4">
                  {certs.map((cert, index) =>(
                    // {cert}
                    <div key={index} className="docs-div mb-10">
                      <li key={index}>
                        <Card onDelete={onDeleteCert} index={index} doc={cert} url={cert.File} type="cert"/>
                      </li>
                    </div>
                  ))}
                </div>
                  </ul> 
              </section>
            </main>
        </div>
        : null
      }
    </>
)
}

export default ListCert