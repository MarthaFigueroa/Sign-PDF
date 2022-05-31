import React, { useEffect, useState } from 'react';
import { storage } from '../../Config/config'
import { toast } from 'react-toastify';
import Searchbar from '../Partials/Searchbar';
import axios from '../../axios.js';
import Card from '../Partials/Card';

const ListCert = () => {
  const [certs, setCerts] = useState([]);

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
            toast('¡El certificado ha sido eliminado exitosamente!', {
                type: 'error',
                autoClose: 2000
            });
            setCerts(res.data);
          })
      }
  }

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
    // const certificates = [];
    // firestore.collection('certificates').onSnapshot(async snapshot => {
    //   snapshot.forEach(async (cert, index) => {
    //     certificates.push({...cert.data(), id: cert.id}); 
    //   });
    //   await setCerts(certificates);
    // });
  }
  useEffect(() => {
    getCerts();
  }, []);
  return (
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
      {/* <Login /> */}
        {/* <PdfTest url={url}/>   */}

      {/* <Preview /> */}
    </div>
  )
}

export default ListCert