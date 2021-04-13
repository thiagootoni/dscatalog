import React from "react";
import ContentLoader from "react-content-loader";
import { generateList } from "core/utils/list";

type Props = {
  repeat: number;
};

const ProductCardLoader = ({ repeat }: Props) => {
  const loaderItens = generateList(repeat);

  return (
    <>
      {loaderItens.map((item) => (
        <ContentLoader
          speed={1}
          width={250}
          height={335}
          viewBox="0 0 250 335"
          backgroundColor="#ecebeb"
          foregroundColor="#d6d2d2"
          key={item}
        >
          <rect x="0" y="60" rx="10" ry="10" width="250" height="335" />
        </ContentLoader>
      ))}
    </>
  );
};

export default ProductCardLoader;
