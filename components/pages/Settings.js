import React from 'react';
import { ScrollView, StyleSheet, View } from 'react-native';
import { Card, Text, Toggle } from '@ui-kitten/components';

export const SettingsScreen = () => {

	const renderItemHeader = (headerProps, info) => (
		<View {...headerProps}>
			<Text category='h6'>
				{info}
			</Text>
		</View>
	);

	const renderItemFooter = (footerProps) => {
		/**
		 * TODO: Add settings saving here maybe and just return the value?
		 */
		return (
			<Text {...footerProps}>
				<Toggle
					style={styles.toggle}
					status='primary'
					checked={true}>
				</Toggle>
			</Text>
		)
	};

	return (
		<ScrollView style={styles.main}>
			<Card
				style={styles.item}
				status='primary'
				header={headerProps => renderItemHeader(headerProps, 'Save incoming Snaps')}
				footer={footerProps => renderItemFooter(footerProps)}>
				<Text>
					Automatically save all recived Snaps and don't mark them as viewed!{'\n'}
				</Text>
			</Card>

			<Card
				style={styles.item}
				status='primary'
				header={headerProps => renderItemHeader(headerProps, 'Save Snapchat stories')}
				footer={footerProps => renderItemFooter(footerProps)}>
				<Text>
					Automatically save all Snapchat stories and don't mark them as viewed!
				</Text>
			</Card>

			<Card
				style={styles.item_last}
				status='primary'
				header={headerProps => renderItemHeader(headerProps, 'Disable screenshot detection')}
				footer={footerProps => renderItemFooter(footerProps)}>
				<Text>
					Disable screenshot detection completely!
				</Text>
			</Card>

			{/* <Card
				style={styles.item_last}
				status='primary'
				header={headerProps => renderItemHeader(headerProps, 'SafetyNet checks')}
				footer={footerProps => renderItemFooter(footerProps)}>
				<Text>
					Automatically check SafetyNet integrity each time you launch Snapchat!
				</Text>
			</Card> */}

		</ScrollView>
	);
};

/**
 * TODO: Use something like: https://github.com/vitalets/react-native-extended-stylesheet
 */

const styles = StyleSheet.create({
	main: {
		flex: 1,
		backgroundColor: '#FBFBFB',
		paddingHorizontal: 8,
		paddingVertical: 10,
	},
	item: {
		marginVertical: 4,
	},
	item_last: {
		marginVertical: 4,
		marginBottom: 20
	}
});