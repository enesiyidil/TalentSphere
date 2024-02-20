import { createContext, useContext, useState, useEffect } from "react";
import { ApiContext } from "./ApiContext";
import PropTypes from "prop-types";

export const AdminContext = createContext();

export const AdminContextProvider = ({ children }) => {
    const { apiGet } = useContext(ApiContext);
};

AdminContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};