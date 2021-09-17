import React from 'react';
import { ImageBackground, StyleSheet, StatusBar } from 'react-native';

export const Header = () => {
	return (
		<>
			<StatusBar translucent backgroundColor="transparent" />
			<ImageBackground
				style={styles.header}
				source={require('../assets/img/Background.gif')}
			/>
		</>
	);
};

const styles = StyleSheet.create({
	header: {
		height: 150,
	},
});