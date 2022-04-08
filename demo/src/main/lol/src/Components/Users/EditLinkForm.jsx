import React, {useState, useEffect} from 'react';
import { firestore } from '../../firebaseConfig'
import { Link, useNavigate} from 'react-router-dom'
import * as Yup from 'yup';
import { Formik, Form, Field, ErrorMessage } from 'formik';

const EditLinkForm = (props) => {

    const phoneRegExp = /^((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?$/;

    const navigate = useNavigate();

    const goTo = (route) =>{
        console.log("kkk");
        navigate(route);
    }

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

    // const initialStateValues = {
    //     firstname: props.EditObject.firstname,
    //     lastname: props.EditObject.lastname,
    //     email: props.EditObject.email,
    //     phone: props.EditObject.phone
    // }

    const [values, setValues] = useState([]);

    const handleSubmit = async(values, { setSubmitting }) =>{
        // e.preventDefault();
        console.log(values);
        await props.editLink(values);
        // await firestore.collection('links').doc(props.id).update(values);
        await setValues({values});
        await goTo('/');
        await props.edit();
    }

    const handleInputChange = (e) =>{
        const { name, value } = e.target;
        console.log(name, value);
        setValues({...values, [name]: value});
    }

    useEffect(() => {
        const getObject = () =>{
            console.log(props.id);
            firestore.collection('links').doc(props.id).get().then(snapshot => setValues(snapshot.data()))
        }
        getObject();
    }, [props.id]);

    return(
        <>        
            <Formik
                enableReinitialize="true"
                initialValues = {{
                    firstname: values.firstname,
                    lastname: values.lastname,
                    email: values.email,
                    phone: values.phone
                }}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
            >
            {({ isSubmitting }) => (
            <Form>
                <ErrorMessage name="firstname" component="div" />
                <ErrorMessage name="lastname" component="div" />
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">person</i>
                    </div>
                    <Field type="text" name="firstname" placeholder="Firstname" className="form-control" onChange={handleInputChange} value={values.firstname || ""} />
                    <Field type="text" name="lastname" placeholder="Lastname" className="form-control" onChange={handleInputChange} value={values.lastname || ""} />
                </div>
                <ErrorMessage name="email" component="div" />
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">email</i>
                    </div>
                    <Field type="text" name="email" placeholder="Email" className="form-control" onChange={handleInputChange} value={values.email || ""} />
                </div>
                <ErrorMessage name="phone" component="div" />
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">phone</i>
                    </div>
                    <Field type="text" name="phone" placeholder="Phone" className="form-control" onChange={handleInputChange} value={values.phone || ""} />
                </div>
                <div className="mx-auto text-center">
                    <button type="submit" className="btn btn-primary" disabled={isSubmitting}>Update</button>
                </div>
                <Link className="btn btn-light" to="/">Cancel</Link>
            </Form>
            )}
            </Formik>    
            {/* <form onSubmit={handleSubmit}>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">person</i>
                    </div>
                    <input type="text" name="firstname" placeholder="Firstname" className="form-control" onChange={handleInputChange} value={values.firstname || ""} autoFocus />
                    <input type="text" name="lastname" placeholder="Lastname" className="form-control" onChange={handleInputChange} value={values.lastname || ""} />
                </div>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">email</i>
                    </div>
                    <input type="text" name="email" placeholder="Email" className="form-control" onChange={handleInputChange} value={values.email || ""} />
                </div>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">phone</i>
                    </div>
                    <input type="text" name="phone" placeholder="Phone" className="form-control" onChange={handleInputChange} value={values.phone || ""} />
                </div>
                <div className="form-group mx-auto text-center">
                    <button className="btn btn-primary">Add</button>
                    <Link className="btn btn-info" to="/">Cancel</Link>
                </div>
            </form> */}
            {/* <button className="btn btn-info" onClick={()=>goTo('/')}>cancel</button> */}
        </>
    )
}

export default EditLinkForm;