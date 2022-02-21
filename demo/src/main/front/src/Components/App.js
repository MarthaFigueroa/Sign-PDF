import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AddLinks from '../Pages/AddLinks';
import UploadFiles from '../Pages/UploadFiles';
import EditLinks from '../Pages/EditLinks';
import ListLink from '../Pages/ListLinks';

function App() {
  return (
        <Router>
            <Routes>
                <Route exact path="/" element={<ListLink />} />

                {/* Prestamso */}
                {/* <Route exact path="/prestamos" component={prestamos} /> */}
                <Route exact path="/uploadFile" element={<UploadFiles />} />
                <Route exact path="/newLink" element={<AddLinks />} />
                <Route exact path="/editLink/:id" element={<EditLinks />} />
            </Routes>
        </Router>
    )   
}

export default App;

