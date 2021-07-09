import makeRequest from "core/utils/request";
import React, { useState } from "react";
import BaseForm from "../../BaseForm";
import "./styles.scss";

type FormEvent = React.ChangeEvent<
    HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
>;

type FormState = {
    name: string;
    price: string;
    category: string;
    description: string;
    imgUrl: string;
};

const Form = () => {
    const [formData, setFormData] = useState<FormState>({
        name: "",
        price: "",
        category: "1",
        description: "",
        imgUrl:
            "https://tecnoblog.net/wp-content/uploads/2020/11/playstation_5_produto.png",
    });

    const handleOnChange = (event: FormEvent) => {
        const name = event.target.name;
        const value = event.target.value;

        setFormData((data) => ({ ...data, [name]: value }));
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const payload = {
            ...formData,
            categories: [
                {
                    id: formData.category,
                },
            ],
        };

        makeRequest({ method: "POST", url: "/products", data: payload }).then(
            () => {
                setFormData({
                    ...formData,
                    name: "",
                    category: "",
                    price: "",
                    description: "",
                });
            }
        );
    };

    return (
        <form onSubmit={handleSubmit}>
            <BaseForm title="cadastrar produto">
                <div className="row">
                    <div className="col-6">
                        <input
                            type="text"
                            name="name"
                            className="form-control"
                            onChange={handleOnChange}
                            placeholder="nome do produto"
                        />
                        <select name="category" onChange={handleOnChange}>
                            <option value="1">Livros</option>
                            <option value="2">Livros</option>
                            <option value="3">Livros</option>
                        </select>
                        <input
                            type="text"
                            name="price"
                            className="form-control"
                            onChange={handleOnChange}
                            placeholder="preço do produto"
                        />
                    </div>
                    <div className="col-6">
                        <textarea
                            name="description"
                            cols={30}
                            rows={10}
                            onChange={handleOnChange}
                        ></textarea>
                    </div>
                </div>
            </BaseForm>
        </form>
    );
};

export default Form;
