import React from "react";
import {SearchButton, SearchContainer, SearchInput} from "../layout/LocationSearch.style.js"

let LocationSearch = () => {

    return(
        <SearchContainer>
            <SearchInput placeholder="지역을 입력하세요 (예: 서울)" />&nbsp;&nbsp;
            <SearchButton>검색</SearchButton>
        </SearchContainer>
    );
}

export default LocationSearch;


