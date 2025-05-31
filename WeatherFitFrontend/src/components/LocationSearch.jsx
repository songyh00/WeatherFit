import React, { useState, useEffect, useRef } from "react";
import {
    SearchButton,
    SearchContainer,
    SearchInput
} from "../layout/LocationSearch.style.js";

const LocationSearch = ({ onSearchComplete }) => {
    const [inputAddress, setInputAddress] = useState("");
    const [searchedAddress, setSearchedAddress] = useState("");
    const [isFocused, setIsFocused] = useState(false);
    const [selectedDate, setSelectedDate] = useState("today");
    const inputRef = useRef(null);

    // 🔸 저장된 검색값 불러오기
    useEffect(() => {
        const savedAddress = localStorage.getItem("savedAddress") || "서울시 종로구";
        const savedDate = localStorage.getItem("savedDate") || "today";
        setInputAddress(""); // 입력창은 비우고
        setSearchedAddress(savedAddress);
        setSelectedDate(savedDate);

        // 초기 로딩 시 한 번만 검색
        onSearchComplete(savedAddress, savedDate);
    }, []);

    const handleSearch = () => {
        const trimmed = inputAddress.trim();
        if (!trimmed) {
            alert("지역을 입력해주세요.");
            return;
        }

        setSearchedAddress(trimmed);
        setInputAddress("");
        inputRef.current.blur();

        // 🔸 로컬스토리지에 저장
        localStorage.setItem("savedAddress", trimmed);
        localStorage.setItem("savedDate", selectedDate);

        onSearchComplete(trimmed, selectedDate);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
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

                <select
                    value={selectedDate}
                    onChange={(e) => {
                        const newDate = e.target.value;
                        setSelectedDate(newDate);
                        localStorage.setItem("savedDate", newDate);
                        // 날짜만 바꿔도 자동으로 검색
                        const addressToUse = searchedAddress || "서울시 종로구";
                        onSearchComplete(addressToUse, newDate);
                    }}
                    style={{ padding: "8px", fontSize: "14px" }}
                >
                    <option value="today">오늘</option>
                    <option value="tomorrow">내일</option>
                </select>

                &nbsp;&nbsp;
                <SearchButton type="submit">검색</SearchButton>
            </SearchContainer>
        </form>
    );
};

export default LocationSearch;
