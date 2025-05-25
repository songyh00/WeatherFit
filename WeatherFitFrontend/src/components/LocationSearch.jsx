import React, { useState, useRef } from "react";
import { SearchButton, SearchContainer, SearchInput } from "../layout/LocationSearch.style.js";

const LocationSearch = ({ onSearchComplete }) => {
    const [inputAddress, setInputAddress] = useState("");
    const [searchedAddress, setSearchedAddress] = useState("");
    const [isFocused, setIsFocused] = useState(false);
    const inputRef = useRef(null);

    const handleSearch = () => {
        const trimmed = inputAddress.trim();
        if (!trimmed) {
            alert("지역을 입력해주세요.");
            return;
        }
        setSearchedAddress(trimmed);
        onSearchComplete(trimmed);
        setInputAddress("");
        inputRef.current.blur();
    };

    const handleSubmit = (e) => {
        e.preventDefault(); // 🔸 폼 제출 기본 동작 방지
        handleSearch();
    };

    return (
        <form onSubmit={handleSubmit}>
            <SearchContainer>
                <SearchInput
                    ref={inputRef}
                    placeholder={
                        isFocused
                            ? "지역을 입력하세요 (예: 서울시 강남구)"
                            : searchedAddress || "지역을 입력하세요 (예: 서울시 강남구)"
                    }
                    value={inputAddress}
                    onChange={(e) => setInputAddress(e.target.value)}
                    onFocus={() => setIsFocused(true)}
                    onBlur={() => setIsFocused(false)}
                />
                &nbsp;&nbsp;
                <SearchButton type="submit">검색</SearchButton>
            </SearchContainer>
        </form>
    );
};

export default LocationSearch;
