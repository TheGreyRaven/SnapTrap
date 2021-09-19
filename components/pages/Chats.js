import React from 'react';
import { Linking, StyleSheet, View } from 'react-native';
import { Button, Card, Layout, Text } from '@ui-kitten/components';

const Header = (props) => (
	<View {...props}>
		<Text category='h5' style={styles.warningHead} status='primary'>WE ARE SORRY!</Text>
		<Text category='s1'>This function is not yet available! :(</Text>
	</View>
);

const Footer = (props) => (
	<View {...props} style={[props.style, styles.footerContainer]}>
		<Button
			style={styles.btn}
			size='small'
			onPress={() => { Linking.openURL('https://discord.gg/invite/tuA4Q4UYDh') }}
		>
			DISCORD
		</Button>
	</View>
);


export const ChatScreen = () => (
	<Layout style={styles.main}>
		<Card header={Header} footer={Footer} status='primary'>
			<Text>Don't worry we will soon add functionallity to automatically save all Snapchat messages!{'\n'}{'\n'}

			The reason to why we don't release all functions at once is becuse we want to make sure everything 100% works first of all and most importantly make sure that it's safe to use!{'\n'}{'\n'}

			You can follow our progression over at Discord, until then you will just have to wait!{'\n'}{'\n'}
			</Text>
			<Text style={{ fontWeight: 'bold' }} status='primary'>
				- SnapTrap Team
			</Text>
		</Card>
	</Layout>
);

const styles = StyleSheet.create({
	main: {
		flex: 1,
		backgroundColor: '#FBFBFB',
		paddingHorizontal: 8,
		paddingVertical: 10,
	},
	footerContainer: {
		flexDirection: 'row',
		justifyContent: 'center',
	},
	btn: {
		marginHorizontal: 2,
	},
	warningHead: {
		fontWeight: "bold"
	}
});