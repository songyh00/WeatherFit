import React from 'react';
import {
    WeatherCardWrap,
    RegionText,
    WeatherText,
    IconAndTempRow,
    IconBox,
    TemperatureBig,
    TimeLabel
} from "../layout/WeatherSection.style.js";
import WeatherDisplay from "./WeatherType.jsx";

const WeatherSection = ({ weatherData, address }) => {
    const defaultAddress = "서울시 종로구";
    const targetAddress = address?.trim() ? address : defaultAddress;

    const now = new Date();
    const hour = now.getHours();
    const fcstTime = String(hour).padStart(2, '0') + '00';

    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');

    const currentWeather = weatherData?.hourlyTemperatures?.find(w =>
        w.month === month && w.day === day && w.time === fcstTime
    );

    if (!currentWeather) {
        return <WeatherCardWrap>날씨 정보를 불러올 수 없습니다.</WeatherCardWrap>;
    }

    const displayTime = `${month}월 ${day}일 ${hour < 12 ? '오전' : '오후'} ${hour % 12 === 0 ? 12 : hour % 12}시`;

    return (
        <WeatherCardWrap>
            <RegionText>{targetAddress}</RegionText>
            <WeatherText>{currentWeather.weatherType}</WeatherText>
            <IconAndTempRow>
                <IconBox>
                    <WeatherDisplay weatherType={currentWeather.weatherType} />
                </IconBox>
                <TemperatureBig>{currentWeather.temperature}°</TemperatureBig>
            </IconAndTempRow>
            <TimeLabel>{displayTime}</TimeLabel>
        </WeatherCardWrap>
    );
};

export default WeatherSection;
