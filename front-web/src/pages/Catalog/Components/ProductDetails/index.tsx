import React from 'react';
import { useParams } from 'react-router';
import { Link } from 'react-router-dom';
import { ReactComponent as ArrowIcon} from '../../../../core/assets/images/arrow.svg';
import { ReactComponent as ProducImage} from '../../../../core/assets/images/product.svg'
import ProductPrice from '../../../../core/components/ProductPrice';
import "./styles.scss";

type ParamsType = {
    productId: string;
}

const ProductDetails = () =>{

    const { productId } = useParams<ParamsType>();

    return(        
        <div className="product-details-container card-base bd-radius-20">
           
            <Link to="/products" className="go-back-container">
                <ArrowIcon className="arrow-icon"/>
                <h1 className="text-go-back">voltar</h1>
            </Link>

            <div className="row">
                <div className="col-6 pr-5">
                    <div className = "card-product-details bd-radius-20 text-center">
                        <ProducImage className="product-details-image"/>
                    </div>

                    <div className="product-details-info-container">
                        <h1 className="product-name">Computador Desktop - Intel Core i7</h1>
                        <ProductPrice price="2899,00" />
                    </div>
                </div>
                
                <div className="col-6 card-product-details bd-radius-20">                    
                    <h1 className="text-title-description">Descrição do Produto</h1>
                    <p className="text-description">
                        Seja um mestre em multitarefas com a capacidade para exibir quatro aplicativos simultâneos na tela. A tela está ficando abarrotada? Crie áreas de trabalho virtuais para obter mais espaço e trabalhar com os itens que você deseja. Além disso, todas as notificações e principais configurações são reunidas em uma única tela de fácil acesso.
                    </p>                                      
                </div>
            </div>
           
        </div>
    );   
};

export default ProductDetails;