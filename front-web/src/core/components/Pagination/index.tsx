import { generateList } from "core/utils/list";
import { ReactComponent as ArrowIcon } from "core/assets/images/arrow.svg";
import React from "react";
import "./styles.scss";

type Props = {
  totalPages: number;
  activePage: number;
  changePage: (item: number) => void;
};

const Pagination = ({ totalPages, activePage, changePage }: Props) => {
  const pages = generateList(totalPages);
  const previousClass = (totalPages > 0 && activePage > 0) ? 'active-row' : 'inactive-row';
  const nextClass =  (totalPages > 0 && (totalPages > activePage + 1)) ? 'active-row' : 'inactive-row'; 

  return (
    <div className="pagination-container">
      <ArrowIcon className={`arrow previous-arrow ${previousClass}`} />

      {pages.map((item) => (
        <div 
            className={`pagination-item ${activePage === item ? "active" : ""}`}
            onClick={() => changePage(item)}
            key={item}
        >
          {item + 1}
        </div>
      ))}

      <ArrowIcon className={`arrow next-arrow ${nextClass}`} />
    </div>
  );
};

export default Pagination;
