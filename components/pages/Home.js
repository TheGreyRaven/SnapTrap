import React from 'react';
import { Linking, StyleSheet, View } from 'react-native';
import { Button, Card, Layout, Text } from '@ui-kitten/components';
import { UpdateModal } from '../UpdateModal';

const Header = (props) => (
	<View {...props}>
		<Text category='h5' style={styles.warningHead} status='primary'>WARNING!</Text>
		<Text category='s1'>Please read before continuing!</Text>
	</View>
);


const Footer = (props) => (
	<View {...props} style={[props.style, styles.footerContainer]}>
		<Button
			style={styles.btn}
			size='small'
			onPress={() => { Linking.openURL('https://discord.gg/tuA4Q4UYDh') }}
		>
			DISCORD
		</Button>
		<Button
			style={styles.btn}
			size='small'
			status='success'
			onPress={() => { Linking.openURL('https://www.paypal.com/donate?hosted_button_id=JZC6HHA7S9G84') }}
		>
			SUPPORT PROJECT
		</Button>
		<Button
			style={styles.btn}
			size='small'
			onPress={() => { Linking.openURL('https://github.com/TheGreyRaven/SnapTrap') }}
		>
			GITHUB
		</Button>
	</View>
);


export const HomeScreen = () => {
	return (
	<>
		<UpdateModal/>
		<Layout style={styles.main}>
			<Card header={Header} footer={Footer} status='primary'>
				<Text>First of all thank you for using SnapTrap!{'\n'}{'\n'}

					As you might know this app is currently in heavy development so there will be bugs and issues, so if you find an issue / bug please let us know on either Discord or Github!{'\n'}{'\n'}

					We can't guarantee that this app will forever be working or safe to use so please disable automatic updates in Googe Play,
					we will try our best to make sure that the app will work for future version of Snapchat and mainly be safe to use!{'\n'}{'\n'}

					USE AT YOUR OWN RISK!{'\n'}
				</Text>
				<Text style={{ fontWeight: 'bold' }} status='primary'>
					- SnapTrap Team
				</Text>
			</Card>
		</Layout>
	</>
	)
};

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
	},
	backdrop: {
		backgroundColor: 'rgba(0, 0, 0, 0.5)',
	}
});