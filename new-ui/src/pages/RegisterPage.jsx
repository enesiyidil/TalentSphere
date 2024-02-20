import {useForm} from "react-hook-form";
import {useContext} from "react";
import {RegisterContext} from "../context/RegisterContext.jsx";
import styles from "../Css/RegisterPage.module.css";

const RegisterPage = () => {

    const {handleRegister} = useContext(RegisterContext);

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
    }

    return (
        <>
            <div className={styles["register-form-wrapper"]}>
                <form onSubmit={handleSubmit} className={styles["register-form"]}>
                    <label className={styles["label"]}>
                        <span>Name: </span>
                        <input
                            {...register("name", {
                                required: `Cannot be left blank!`,
                            })}
                            type={'text'}
                            placeholder={'Name'}
                            className={styles["textarea"]}
                        />
                        {errors['name'] && (
                            <p className="text-red-500">{errors['name'].message}</p>
                        )}
                    </label>

                    <label className={styles["label"]}>
                        <span>Surname: </span>
                        <input
                            {...register("surname", {
                                required: `Cannot be left blank!`,
                            })}
                            type={'text'}
                            placeholder={'Surname'}
                            className={styles["textarea"]}
                        />
                        {errors['surname'] && (
                            <p className="text-red-500">{errors['surname'].message}</p>
                        )}
                    </label>

                    <label className={styles["label"]}>
                        <span>Username: </span>
                        <input
                            {...register("username", {
                                required: `Cannot be left blank!`,
                            })}
                            type={'text'}
                            placeholder={'username'}
                            className={styles["textarea"]}
                        />
                        {errors['username'] && (
                            <p className="text-red-500">{errors['username'].message}</p>
                        )}
                    </label>

                    <label className={styles["label"]}>
                        <span>E-Mail: </span>
                        <input
                            {...register("email", {
                                required: `Cannot be left blank!`,
                            })}
                            type={'email'}
                            placeholder={'E-Mail'}
                            className={styles["textarea"]}
                        />
                        {errors['email'] && (
                            <p className="text-red-500">{errors['email'].message}</p>
                        )}
                    </label>

                    <label className={styles["label"]}>
                        <span>Password: </span>
                        <input
                            {...register("password", {
                                required: `Cannot be left blank!`,
                            })}
                            type={'password'}
                            placeholder={'Password'}
                            className={styles["textarea"]}
                            minLength="4"
                        />
                        {errors['password'] && (
                            <p className="text-red-500">{errors['password'].message}</p>
                        )}
                    </label>

                    <label className={styles["label"]}>
                        <span>RePassword: </span>
                        <input
                            {...register("rePassword", {
                                required: `Cannot be left blank!`,
                            })}
                            type={'password'}
                            placeholder={'Password'}
                            className={styles["textarea"]}
                            minLength="4"
                        />
                        {errors['rePassword'] && (
                            <p className="text-red-500">{errors['rePassword'].message}</p>
                        )}
                    </label>

                    <div>
                        <button disabled={isSubmitting} type="submit" className={styles["button"]}>
                            Submit
                        </button>
                        <button type='button' onClick={handleClearClick} className={styles["button"]}>Clear</button>
                    </div>
                </form>
            </div>
        </>
    )
}

export default RegisterPage;