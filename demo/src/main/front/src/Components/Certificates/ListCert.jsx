import React, { useEffect, useState } from 'react';
import { firestore, storage } from '../../firebaseConfig'
import { toast } from 'react-toastify';
import Searchbar from '../ExtraComponents/Searchbar';
import Card from '../ExtraComponents/Card';
// import PdfTest from '../ExtraComponents/PdfTest'
// import Preview from '../ExtraComponents/Preview'
// import { useNavigate } from 'react-router-dom'
// import Login from '../ExtraComponents/Login';
// import Card from '../ExtraComponents/Card';

const ListCert = () => {
  const [certs, setCerts] = useState([]);

  // const navigate = useNavigate();

  // const goTo = (route) =>{
  //     console.log("kkk");
  //     navigate(route);
  // }

  const onDeleteCert = async (id, filename) =>{
      console.log(id);
      const confirmation = window.confirm("Are you sure you want to delete this certificate?");
      if(confirmation===true){
          await firestore.collection('certificates').doc(id).delete();
          let imageRef = storage.refFromURL(`gs://validacion-de-documentos.appspot.com/certificates/${filename}`);
          await imageRef.delete();
          toast('Certificate Removed Successfully', {
              type: 'error',
              autoClose: 2000
          });
          // await goTo('/documents');
      }
  }

  const getCerts = () =>{
    const certificates = [];
    firestore.collection('certificates').onSnapshot(async snapshot => {
      snapshot.forEach(async (cert, index) => {
        // storage.ref(`/originalDocuments/${doc.data().OriginalFile.Filename}`).getDownloadURL()
        //   .then((url) => {
        //     console.log("Download URL:",url);
        //     docUrls.push({...url, id: doc.id});
        // })
        // console.log("Index:",docUrls[index]);
        // console.log(doc.data());
        certificates.push({...cert.data(), id: cert.id}); 
        // console.log(doc.data().SignedFile.CSV);
          // docUrls.push([doc.data().OriginalFile.File])
          // storage.ref().getDownloadURL()
      });
      // console.log(docs);
      await setCerts(certificates);
      // setUrl(urls);
      // setDocUrl(docUrls);

    });
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
                <div key={index} className="col-md-4 docs-div">
                  <li key={index}>
                    <Card onDeleteDoc={onDeleteCert} index={index} doc={cert} url={cert.File} type="cert"/>
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