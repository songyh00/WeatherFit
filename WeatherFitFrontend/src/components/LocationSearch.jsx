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
        const savedAddress = localStorage.getItem("savedAddress");
        const savedDate = localStorage.getItem("savedDate") || "today";

        setInputAddress(savedAddress || ""); // 검색창에 보여줄 입력값 설정
        setSearchedAddress(savedAddress || "");
        setSelectedDate(savedDate);

        onSearchComplete(savedAddress || "서울시 종로구", savedDate);
    }, []);

    const handleSearch = () => {
        const trimmed = inputAddress.trim();
        if (!trimmed) {
            alert("지역을 입력해주세요.");
            return;
        }

        setSearchedAddress(trimmed);   // placeholder용 주소 설정
        setInputAddress(trimmed);      // 입력창에 그대로 유지 ← ✅ 핵심 변경
        inputRef.current.blur();

        // 저장
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
                    placeholder="지역을 입력하세요 (예: 서울시 강남구)"
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

                        const addressToUse = inputAddress.trim() || "서울시 종로구";
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
