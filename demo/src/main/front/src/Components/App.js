import React from 'react';
import 'tailwindcss/tailwind.css'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ListDocuments from '../Pages/Docs/ListCards';
import AddLinks from '../Pages/Links/AddLinks';
import UploadFiles from '../Pages/Docs/UploadFiles';
import EditLinks from '../Pages/Links/EditLinks';
// import ListDocs from '../Pages/Docs/ListDocs';
import ValidateFiles from '../Pages/Docs/ValidateFiles';
import ListLinks from '../Pages/Links/ListLinks';
import LoginPage from '../Pages/Extra/LoginPage';
import ListPruebas from '../Pages/Extra/ListPruebas';
import ListCerts from '../Pages/Certs/ListCerts';
import UploadCerts from '../Pages/Certs/UploadCerts';
import '../public/css/tailwind.css'
import '../public/css/styles.css';


function App() {
  return (
        <Router>
            <Routes>
                <Route exact path="/" element={<LoginPage />} />
                <Route exact path="/links" element={<ListLinks />} />
                <Route exact path="/pruebas" element={<ListPruebas />} />
                <Route exact path="/documents" element={<ListDocuments />} />
                <Route exact path="/certificates" element={<ListCerts />} />
                {/* <Route exact path="/documents" element={<ListDocs />} /> */}
                <Route exact path="/validateDocuments" element={<ValidateFiles />} />
                <Route exact path="/uploadFile" element={<UploadFiles type='doc' />} />
                <Route exact path="/uploadCert" element={<UploadCerts />} />
                <Route exact path="/newLink" element={<AddLinks />} />
                <Route exact path="/editLink/:id" element={<EditLinks/>} />
            </Routes>
        </Router>
    )   
}

export default App;

