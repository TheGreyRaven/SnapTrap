import React, { useEffect, useState } from 'react';
import { Linking, ScrollView, StyleSheet, View } from 'react-native';
import { Button, Card, Modal, Text } from '@ui-kitten/components';
import VersionNumber from 'react-native-version-number';

const UpdateHeader = (props) => (
	<View {...props}>
		<Text category='h5' style={styles.warningHead} status='primary'>NEW UPDATE! ({props})</Text>
		<Text category='s1'>We highly recommend you to update!</Text>
	</View>
)

export const UpdateModal = () => {
	const [visible, setVisible] = useState(false);
	const [data, setData] 		= useState([]);

	const getUpdate = async () => {
		try {
			const response = await fetch('https://api.snaptrap.io/get-latest');
			const update = await response.json();
			setData(update)
			if (update.version !== VersionNumber.appVersion) {
				setVisible(true)
			}
		} catch (error) {
			console.error(error);
		}
	}

	const downloadUpdate = (url) => {
		Linking.openURL(url)
		setVisible(false)
	}

	useEffect(() => {
		//getUpdate();
	}, []);

	return (
		<Modal visible={visible} backdropStyle={styles.backdrop}>
			<Card disabled={true} header={UpdateHeader(data.version)}>
				<Text>A new version of SnapTrap is available! 🥳{'\n'}</Text>
				<ScrollView style={styles.changelog}>
					<Text>
						{data.change_log}
					</Text>
				</ScrollView>

				<View style={styles.footerContainer}>
					<Button style={styles.btn} onPress={() => setVisible(false)}>
						DISMISS
					</Button>

					<Button style={styles.btn} status='success' onPress={() => downloadUpdate(data.download_url)}>
						DOWNLOAD
					</Button>
				</View>
			</Card>
		</Modal>
	)
}

const styles = StyleSheet.create({
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
	},
	changelog: {
		maxHeight: 200,
		marginBottom: 10
	}
});