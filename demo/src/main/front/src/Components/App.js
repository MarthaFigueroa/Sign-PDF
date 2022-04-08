import React from 'react';
import 'tailwindcss/tailwind.css'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AddLinks from '../Pages/Links/AddLinks';
import UploadFiles from '../Pages/Docs/UploadFiles';
import EditLinks from '../Pages/Links/EditLinks';
import ListLinks from '../Pages/Links/ListLinks';
import ListDocs from '../Pages/Docs/ListDocs';
import ListSignedDocs from '../Pages/Docs/ListSignedDocs';
import ListCards from '../Pages/Docs/ListCards';
import LoginPage from '../Pages/Extra/LoginPage';
import ListPruebas from '../Pages/Extra/ListPruebas';

function App() {
  return (
        <Router>
            <Routes>
                <Route exact path="/" element={<LoginPage />} />
                <Route exact path="/links" element={<ListLinks />} />
                <Route exact path="/pruebas" element={<ListPruebas />} />
                <Route exact path="/documents" element={<ListDocs />} />
                <Route exact path="/cards" element={<ListCards />} />
                <Route exact path="/signedDocuments" element={<ListSignedDocs />} />

                {/* Prestamso */}
                {/* <Route exact path="/prestamos" component={prestamos} /> */}
                <Route exact path="/uploadFile" element={<UploadFiles />} />
                <Route exact path="/newLink" element={<AddLinks />} />
                <Route exact path="/editLink/:id" element={<EditLinks/>} />
            </Routes>
        </Router>
    )   
}

export default App;

