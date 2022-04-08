import React, {useState} from 'react';
import { Link, useNavigate } from 'react-router-dom'
import * as Yup from 'yup';
import { Formik, Form, Field, ErrorMessage } from 'formik';
// import 'bootswatch/dist/minty/bootstrap.min.css';

const LinkForm = (props) => {

    const phoneRegExp = /^((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?$/;

    const validationSchema = Yup.object().shape({
        firstname: Yup.string()
            .min(3, 'Muy Corto!')
            .max(50, 'Muy Largo!')
            .required('First Name is required*'),
        lastname: Yup.string()
            .min(3, 'Muy Corto!')
            .max(50, 'Muy Largo!')
            .required('Last Name is required*'),
        email: Yup.string()
            .min(3, 'Muy Corto!')
            .max(50, 'Muy Largo!')
            .email('Email is invalid')
            .required('Email is required*'),
        phone: Yup.string()
            .matches(phoneRegExp, 'Phone number is not valid*')
            .required('Phone Number is required*'),
    });

    const navigate = useNavigate();

    const goTo = (route) =>{
        console.log("kkk");
        navigate(route);
    }

    const [values, setValues] = useState([]);

    const handleSubmit = async(values) =>{
        // e.preventDefault();
        await props.addOrEditLink(values);
        console.log(values);
        await setValues({values});
        await goTo('/');
        props.add();
    }

    const handleInputChange = (e) =>{
        const { name, value } = e.target;
        setValues({...values, [name]: value});
    }

    return(
        <>            
            <Formik
                enableReinitialize="true"
                initialValues = {{
                    firstname: values.firstname || "",
                    lastname: values.lastname || "",
                    email: values.email || "",
                    phone: values.phone || ""
                }}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
            >
            {({ isSubmitting }) => (
            <Form>
                <ErrorMessage name="firstname" component="div" />
                <ErrorMessage name="lastname" component="div" />
                <div className="form-group input-group formField">
                    <div className="input-group-text icon">
                        <i className="material-icons">person</i>
                    </div>
                    <Field type="text" name="firstname" placeholder="Firstname" className="form-control" onChange={handleInputChange} value={values.firstname || ""} />
                    <Field type="text" name="lastname" placeholder="Lastname" className="form-control" onChange={handleInputChange} value={values.lastname || ""} />
                </div>
                <ErrorMessage name="email" component="div" />
                <div className="form-group input-group formField">
                    <div className="input-group-text icon">
                        <i className="material-icons">email</i>
                    </div>
                    <Field type="text" name="email" placeholder="Email" className="form-control" onChange={handleInputChange} value={values.email || ""} />
                </div>
                <ErrorMessage name="phone" component="div" />
                <div className="form-group input-group formField">
                    <div className="input-group-text icon">
                        <i className="material-icons">phone</i>
                    </div>
                    <Field type="text" name="phone" placeholder="Phone" className="form-control" onChange={handleInputChange} value={values.phone || ""} />
                </div>
                <div className="mx-auto text-center">
                    <button type="submit" className="btn btn-create" disabled={isSubmitting}>Add</button>
                </div>
                <Link className="btn btn-light" to="/">Cancel</Link>
            </Form>
            )}
            </Formik> 
        </>
    )
}

export default LinkForm;