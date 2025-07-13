import Foundation

func dateToString(date: Date,format: String)->String{
	let formatter = DateFormatter()
	formatter.dateFormat = format
	return formatter.string(from: date)
}
