import React from 'react';
import Searchbar from '../Partials/Searchbar';
import Card from '../Partials/Card';

const ListCert = ({certs, onDeleteCert}) => {

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