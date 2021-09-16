import AsyncStorage from '@react-native-async-storage/async-storage';

export const storeValue = async (key, value) => {
	if (value) {
		try {
			await AsyncStorage.setItem(`${key}`, JSON.stringify(value))
			return value
		} catch (e) {
			console.log('ERROR SAVING', e)
			return false
		}
	} else {
		try {
			const value = await AsyncStorage.getItem(`${key}`)
			if (value !== null) {
				return value
			}
			return false
		} catch (e) {
			console.log('ERROR READING', e)
			return false
		}
	}
}