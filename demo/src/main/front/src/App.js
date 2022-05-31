import React from 'react';
import 'tailwindcss/tailwind.css';
import './public/css/tailwind.css'
import './public/css/styles.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ListDocuments from './Pages/Docs/ListDocs';
import ListUsers from './Pages/Users/ListUsers';
import UploadFiles from './Pages/Docs/UploadFiles';
import EditUsers from './Pages/Users/EditUsers';
// import ListDocs from './Pages/Docs/ListDocs';
import ValidateFiles from './Pages/Docs/ValidateFiles';
import LoginPage from './Pages/Users/LoginPage';
import ListCerts from './Pages/Certs/ListCerts';
import UploadCerts from './Pages/Certs/UploadCerts';
import RegisterPage from './Pages/Users/RegisterPage';
import ProfilePage from './Pages/Users/ProfilePage';
import UserProvider from "./Providers/UserProvider";


function App() {
  return (
    <UserProvider>
        <Router>
            <Routes>
                <Route exact path="/" element={<LoginPage />} />
                <Route exact path="/register" element={<RegisterPage />} />
                <Route exact path="/profile" element={<ProfilePage />} />
                <Route exact path="/documents" element={<ListDocuments />} />
                <Route exact path="/users" element={<ListUsers />} />
                <Route exact path="/certificates" element={<ListCerts />} />
                <Route exact path="/validateDocuments" element={<ValidateFiles />} />
                <Route exact path="/uploadFile" element={<UploadFiles type='doc' />} />
                <Route exact path="/uploadCert" element={<UploadCerts />} />
                <Route exact path="/editUser/:id" element={<EditUsers/>} />
            </Routes>
        </Router>
    </UserProvider>
    )   
}

export default App;

