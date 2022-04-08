import React from 'react'

const CreatedCert= (props) => {

  return (
      <>
        <div>CreatedCert</div>
        <input type="file" id='cert-selector' onChange={(e)=>{props.cert(e.target.files[0])}} accept=".pfx, .p12, .cer, crt, .p7b, .sst"/>
      </>
  )
}

export default CreatedCert