import React from 'react';
import { useHistory } from 'react-router';
import "./styles.scss";

type Props = {
    title: string;
    children: React.ReactNode;
}

const BaseForm = ({ title, children } : Props) => {

    const history = useHistory();

    const handleCancel = () => {
        history.push('../');
    };

    const handleConfirm = () => {
    };


    return (
        <div className="admin-base-form card-base">
            <h1 className="base-form-title">
                {title}
            </h1>
            {children}
            <div className="base-form-actions">
                <button 
                    className="btn btn-outline-danger btn-lg bd-radius-10 mr-3"
                    onClick={handleCancel}
                >
                    Cancelar
                </button>
                <button 
                className="btn btn-primary btn-lg bd-radius-10 mr-3"
                onClick={handleConfirm}
                >
                    Cadastrar
                </button>
            </div>
        </div>
    );
};

export default BaseForm;