import { createContext, useContext, useState, useEffect } from "react";
import { ApiContext } from "./ApiContext";
import PropTypes from "prop-types";

export const VisitorContext = createContext();

export const VisitorContextProvider = ({ children }) => {
    const { apiGet } = useContext(ApiContext);
};

VisitorContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};