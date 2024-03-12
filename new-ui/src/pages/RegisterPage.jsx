import {useForm} from "react-hook-form";
import React, {useContext} from "react";
import {RegisterContext} from "../context/RegisterContext.jsx";
import styles from "../Css/RegisterPage.module.css";
import {useNavigate} from "react-router-dom";

const RegisterPage = () => {

    const {handleRegister} = useContext(RegisterContext);
    const navigate = useNavigate();

    const {
        register,
        reset,
        formState: {errors, isSubmitting},
        getValues
    } = useForm();

    const handleClearClick = () => {
        reset();
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        handleRegister(getValues());
        navigate('/login');
    }

    return (

        <>
            <div className={styles["register-form-wrapper"]}>
                <form onSubmit={handleSubmit} className={styles["register-form"]}>
                    <div className={styles["label"]}>
                        <span>Name: </span>
                        <input
                            {...register("name", {
                                required: `Cannot be left blank!`,
                            })}
                            type="text"
                            placeholder="Name"
                            className={styles["textarea"]}
                        />
                        {errors["name"] && (
                            <p className="text-red-500">{errors["name"].message}</p>
                        )}

                        <span>Surname: </span>
                        <input
                            {...register("surname", {
                                required: `Cannot be left blank!`,
                            })}
                            type="text"
                            placeholder="Surname"
                            className={styles["textarea"]}
                        />
                        {errors["surname"] && (
                            <p className="text-red-500">{errors["surname"].message}</p>
                        )}

                        <span>Username: </span>
                        <input
                            {...register("username", {
                                required: `Cannot be left blank!`,
                            })}
                            type="text"
                            placeholder="Username"
                            className={styles["textarea"]}
                        />
                        {errors["username"] && (
                            <p className="text-red-500">{errors["username"].message}</p>
                        )}
                        <span>E-Mail: </span>
                        <input
                            {...register("email", {
                                required: `Cannot be left blank!`,
                            })}
                            type="email"
                            placeholder="E-Mail"
                            className={styles["textarea"]}
                        />
                        {errors["email"] && (
                            <p className="text-red-500">{errors["email"].message}</p>
                        )}

                        <span>Password: </span>
                        <input
                            {...register("password", {
                                required: `Cannot be left blank!`,
                            })}
                            type="password"
                            placeholder="Password"
                            className={styles["textarea"]}
                            minLength="4"
                        />
                        {errors["password"] && (
                            <p className="text-red-500">{errors["password"].message}</p>
                        )}

                        <span>RePassword: </span>
                        <input
                            {...register("rePassword", {
                                required: `Cannot be left blank!`,
                            })}
                            type="password"
                            placeholder="Password"
                            className={styles["textarea"]}
                            minLength="4"
                        />
                        {errors["rePassword"] && (
                            <p className="text-red-500">{errors["rePassword"].message}</p>
                        )}

                    </div>

                    <div className={styles["label"]}>
                        <span>Phone: </span>
                        <input
                            {...register("phone", {
                                required: `Cannot be left blank!`,
                            })}
                            type="text"
                            placeholder="Phone"
                            className={styles["textarea"]}
                        />
                        {errors["phone"] && (
                            <p className="text-red-500">{errors["phone"].message}</p>
                        )}
                        <label className={styles["label"]}>Gender:</label>
                        <select {...register("gender", {required: "Please select your gender!"})}
                                className={styles["textarea"]}>
                            <option value="">Select gender</option>
                            <option value="MAN">Man</option>
                            <option value="WOMAN">Woman</option>
                            <option value="NO_GENDER">No Gender</option>
                        </select>
                        {errors["gender"] && (
                            <p className="text-red-500">{errors["gender"].message}</p>
                        )}

                        <span>Photo: </span>
                        <input
                            {...register("photo", {
                                required: `Cannot be left blank!`,
                            })}
                            type="text"
                            placeholder="Photo"
                            className={styles["textarea"]}
                        />
                        {errors["photo"] && (
                            <p className="text-red-500">{errors["photo"].message}</p>
                        )}
                    </div>

                    <div className={styles["button"]}>
                        <button disabled={isSubmitting} type="submit" className={styles[""]}>
                            Submit
                        </button>
                        <button type="button" onClick={handleClearClick} className={styles[""]}>
                            Clear
                        </button>
                    </div>

                </form>
            </div>
        </>
    )
}
export default RegisterPage;