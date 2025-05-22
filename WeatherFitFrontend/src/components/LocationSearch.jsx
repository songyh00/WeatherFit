import React, { useState, useRef } from "react";
import { SearchButton, SearchContainer, SearchInput } from "../layout/LocationSearch.style.js";

const LocationSearch = ({ onSearchComplete }) => {
    const [inputAddress, setInputAddress] = useState("");
    const [searchedAddress, setSearchedAddress] = useState("");
    const [isFocused, setIsFocused] = useState(false);
    const inputRef = useRef(null); // 🔸 input DOM 접근용

    const handleSearch = () => {
        const trimmed = inputAddress.trim();
        if (!trimmed) {
            alert("지역을 입력해주세요.");
            return;
        }
        setSearchedAddress(trimmed);
        onSearchComplete(trimmed);
        setInputAddress("");
        inputRef.current.blur(); // 🔸 엔터 또는 버튼 클릭 후 포커스 해제
    };

    const handleBlur = () => {
        setIsFocused(false);
    };

    const handleFocus = () => {
        setIsFocused(true);
    };

    return (
        <SearchContainer>
            <SearchInput
                ref={inputRef} // 🔸 ref 연결
                placeholder={
                    isFocused
                        ? "지역을 입력하세요 (예: 서울시 강남구)"
                        : searchedAddress || "지역을 입력하세요 (예: 서울시 강남구)"
                }
                value={inputAddress}
                onChange={(e) => setInputAddress(e.target.value)}
                onFocus={handleFocus}
                onBlur={handleBlur}
                onKeyDown={(e) => {
                    if (e.key === "Enter") handleSearch();
                }}
            />
            &nbsp;&nbsp;
            <SearchButton onClick={handleSearch}>검색</SearchButton>
        </SearchContainer>
    );
};

export default LocationSearch;
